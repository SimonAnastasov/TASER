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

export const StripePaymentElement = ({ }) => {
    const stripe = useStripe();
    const elements = useElements();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const account = useSelector(state => state.account);

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

            let headers = {};

            let bearerToken = getCookie("bearerToken");
            if (typeof bearerToken === "string" && bearerToken.length > 0) {
                headers["Authorization"] = `Bearer ${bearerToken}`;
            }

            const error500Message = "Unknown error. This is not an error on your part. Please check back later.";
            axios.post(`${serverApiUrl}/ApiOrder/DoOrder`, {}, {headers})
                .then(response => {
                    const data = response?.data;

                    if (data.isError) {
                        if (data.notLoggedIn) {
                            if (account.loggedIn) {
                                dispatch(setLoggedIn(false));
                                dispatch(setAccount({ email: "" }));
                            }
                            
                            dispatch(setTextError("You are not logged in. Please log in to purchase."));
    
                            return ;
                        }
                    } else {
                        dispatch(setTextSuccess("You've successfully made an order."));
                        navigate("/my-orders");

                        return ;
                    }

                    // Else:
                    let message = data.message;
                    if (!message) message = error500Message;

                    dispatch(setTextError(message));
                })
                .catch(error => {
                    dispatch(setTextError(error500Message));
                })
        }
    };

    return (
        <div className={`fixed inset-0 bg-red-700/20 rounded-xl flex flex-col justify-center items-center transition-all duration-300 ${true ? 'scale-100' : 'scale-0'}`}>
            <div className={`px-4 lg:px-8 py-4 lg:py-8 max-w-5xl mx-auto w-full bg-white rounded-xl flex flex-col justify-center items-center transition-all duration-300 delay-150 overflow-y-auto ${true ? 'scale-100' : 'scale-0'}`}>
                <form onSubmit={handleSubmit}>
                    <PaymentElement />
                    <button className="mx-auto mt-4 --button button--primary-inverted" disabled={!stripe || isProcessing}>Pay ${3} →</button>
                </form>
            </div>
        </div>
    )
}
