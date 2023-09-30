import React, { useEffect, useState } from 'react'
import InfoButton from '../utils/InfoButton'
import { INFO_GET_PAID_PAGE } from '../../utils/infoTexts'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice';
import { getCookie } from '../../utils/functions/cookies';

import axios from 'axios';
import { serverApiUrl } from '../../utils/envVariables';

const GetPaidPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    
    const account = useSelector(state => state?.account)

    const [getPaidHistory, setGetPaidHistory] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [noGetPaidHistoryMessage, setNoGetPaidHistoryMessage] = useState({
        isError: false,
        message: "You have no history or active analyses for improving."
    });

    useEffect(() => {
        if (!account.loggedIn) {
            setIsLoading(false);
            setGetPaidHistory([]);
            setNoGetPaidHistoryMessage({isError: true, message: "You are not logged in. Please log in to view your history or active analyses for improving."});
            return;
        }
        
        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        axios.get(`${serverApiUrl}/api/improvements/getPaidHistory`, {
            headers: headers
        })
            .then(response => {
                const data = response?.data;
                if (!data?.error) {
                    setGetPaidHistory(data.improvementsHistory);
                    setIsLoading(false);
                }
                else {
                    if (data.notLoggedIn) {
                        if (account.loggedIn) {
                            dispatch(setLoggedIn(false));
                            dispatch(setAccount({ username: "" }));
                        }
                        
                        setIsLoading(false);
                        setGetPaidHistory([]);
                        setNoGetPaidHistoryMessage({isError: true, message: "You are not logged in. Please log in to view your history or active analyses for improving."});
                    }
                    else {
                        setIsLoading(false);
                        setGetPaidHistory([]);
                        setNoGetPaidHistoryMessage({isError: true, message: data.message});
                    }
                }
            })
            .catch(error => {
                setIsLoading(false);
                setGetPaidHistory([]);
                setNoGetPaidHistoryMessage({isError: true, message: "Unknown error. Please try again later."});
            })

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [account.loggedIn])

    return (
        <div className="px-6 lg:px-0">
            <p className="heading--3 text-center mb-8">Get Paid - Improve Analyses</p>

            {isLoading ? (
                    <div className="flex justify-center items-center gap-2">
                        <img src="/images/icon-spinner.png" className="w-8 animate-spin" alt="icon spinner"/>
                        <p className="heading--6 text-center">Loading...</p>
                    </div>
            ) : (
                <>
                    {getPaidHistory.length > 0 ? (
                        <>
                            <div className="mb-16 lg:mb-20 w-fit mx-auto flex items-center gap-4">
                                <div className="hidden">
                                    <InfoButton infoText={INFO_GET_PAID_PAGE}/>
                                </div>
                                <button className="--button button--success">Request Analysis For Improving</button>
                                <InfoButton infoText={INFO_GET_PAID_PAGE}/>
                            </div>

                            <hr className="container px-6 lg:px-0 mx-auto bg-primary/20 h-0.5"/>

                            <div className="mt-16 lg:mt-20">
                                {getPaidHistory.map((improvement, index) => (
                                    <div key={index}
                                         className={`${improvement.status === 'finished' ? 'opacity-20' : 'cursor-pointer'} px-8 py-5 lg:py-3 grid grid-cols-1 lg:grid-cols-9 w-full lg:justify-between gap-4 lg:gap-6 rounded-xl bg-primary/10 border-2 border-white transition-all duration-300 hover:bg-primary hover:text-white hover:shadow-md`}
                                         onClick={(e) => improvement.status !== 'finished' ? handleImproveTranscriptionClick(e, improvement.id) : null}     
                                    >
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Filename (By):</p>
                                            <p className="!leading-5 heading--6 flex items-end gap-4">{improvement.filename} (by improvement.employer) <span className={`${improvement.status !== 'finished' ? 'hidden' : 'inline lg:hidden'}`}>→</span></p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Improvement Completed:</p>
                                            <p className="!leading-5 heading--6">{improvement.status === 'finished' ? '✅' : '❌'}</p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Date Created:</p>
                                            <p className="!leading-5 heading--6">{new Date(improvement.timestampCreated).toLocaleString()}</p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Date Updated:</p>
                                            <p className="!leading-5 heading--6">{new Date(improvement.timestampUpdated).toLocaleString()}</p>
                                        </div>
                                        {improvement.status !== 'finished' && (
                                            <div className="hidden lg:flex flex-col overflow-hidden justify-center items-end">
                                                <p className="heading--3">Improve →</p>
                                            </div>
                                        )}
                                    </div>
                                ))}
                            </div>
                        </>
                    ) : (
                        <>
                            <p className={`heading--6 text-center ${noGetPaidHistoryMessage.isError ? 'text-red-700' : ''}`}>{noGetPaidHistoryMessage.isError ? '❌' : ''} {noGetPaidHistoryMessage.message}</p>
                            <div className="mt-2 flex flex-col lg:flex-row gap-4 lg:gap-8 justify-center items-center">
                                <button className="--button button--primary" onClick={handleGoBackHome}>← Go Back Home</button>
                            </div>
                        </>
                    )}
                </>
            )}
        </div>
    )

    function handleGoBackHome(e) {
        navigate("/");
    }

    function handleImproveTranscriptionClick(e, transcriptionId) {

    }
}

export default GetPaidPage