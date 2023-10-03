import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import {useStripe, useElements, PaymentElement} from '@stripe/react-stripe-js';
import { serverApiUrl, baseClientUrl } from '../../utils/envVariables';

import axios from 'axios';

import { setTextError } from '../../redux/reducers/errorsSlice';
import { setTextSuccess } from '../../redux/reducers/successesSlice';
import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice';
import { getCookie } from '../../utils/functions/cookies';
import InfoButton from '../utils/InfoButton';
import { INFO_IMPROVE_THIS_ANALYSIS } from '../../utils/infoTexts';
import { setAnalysisImprovementInfo } from '../../redux/reducers/analysisResultSlice';

export const StripePaymentElement = ({ setIsLoading }) => {
    const stripe = useStripe();
    const elements = useElements();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const account = useSelector(state => state.account);

    const analysis = useSelector(state => state?.analysisResult?.result);
    const improvementInfo = useSelector(state => state?.analysisResult?.improvementInfo);
    const employeeInfo = useSelector(state => state?.analysisResult?.employeeInfo);

    const [isProcessing, setIsProcessing] = useState(false);

    const handleSubmit = async (event) => {
        // We don't want to let default form submission happen here,
        // which would refresh the page.
        event.preventDefault();

        setIsProcessing(true);

        if (!stripe || !elements) {
        // Stripe.js hasn't yet loaded.
        // Make sure to disable form submission until Stripe.js has loaded.
        return;
        }

        const result = await stripe.confirmPayment({
        //`Elements` instance that was used to create the Payment Element
            elements,
            redirect: 'if_required',
            // confirmParams: {
            //     return_url: `${baseClientUrl}/my-orders`,
            // },
        });

        setIsProcessing(false);

        if (result.error) {
            // Show error to your customer (for example, payment details incomplete)
            dispatch(setTextError(result.error.message));
        } else {
            // Your customer will be redirected to your `return_url`. For some payment
            // methods like iDEAL, your customer will be redirected to an intermediate
            // site first to authorize the payment, then redirected to the `return_url`.

            handleImproveRequest(null, analysis?.id, result);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <PaymentElement />
                <div className="mt-4 flex gap-4 items-center">
                    <button className="mx-auto --button button--success" disabled={!stripe || isProcessing}>Improve Your Analysis (${improvementInfo?.cost})</button>
                    <InfoButton infoText={INFO_IMPROVE_THIS_ANALYSIS.replace(/\[\[\[PRICE\]\]\]/g, improvementInfo?.cost ?? '-')}/>
                </div>
            </form>
        </div>
    )

    function handleImproveRequest(e, transcriptionId, result) {
        setIsLoading(true);

        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        axios.post(`${serverApiUrl}/improvements/requestImprovement/${transcriptionId}`, {
            paymentIntent: result
        }, {
            headers: headers
        })
            .then(response => {
                setIsLoading(false);

                const data = response?.data;

                if (!data?.error) {
                    dispatch(setAnalysisImprovementInfo({
                        isRequested: true,
                        improvedBy: data?.improvementRequest?.improvedByCount,
                        deadline: (new Date(new Date(data?.improvementRequest?.timestampCreated).setDate(new Date(data?.improvementRequest?.timestampCreated).getDate() + 7))).toLocaleString()
                    }));
                }
                else {
                    if (data.notLoggedIn) {
                        if (account.loggedIn) {
                            dispatch(setLoggedIn(false));
                            dispatch(setAccount({ username: "" }));

                            dispatch(setTextError("Please log in to request analysis improvement."));                             
                        }
                    }
                    else {
                        dispatch(setTextError(data.message));
                    }
                }
            })
            .catch(error => {
                setIsLoading(false);

                dispatch(setTextError("Unknown error. Please try again later."));
            });
    }
}
