import React, { useEffect, useState } from 'react'
import InfoButton from '../utils/InfoButton'
import { INFO_GET_PAID_PAGE } from '../../utils/infoTexts'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice';
import { getCookie } from '../../utils/functions/cookies';

import axios from 'axios';
import { serverApiUrl } from '../../utils/envVariables';
import { setTextError } from '../../redux/reducers/errorsSlice';
import { setTextSuccess } from '../../redux/reducers/successesSlice';
import { setAnalysisEmployeeInfo, setAnalysisResult } from '../../redux/reducers/analysisResultSlice';

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

        axios.get(`${serverApiUrl}/improvements/getPaidHistory`, {
            headers: headers
        })
            .then(response => {
                setIsLoading(false);

                const data = response?.data;

                if (!data?.error) {
                    setGetPaidHistory(data.improvementsHistory);

                    if (data.improvementsHistory.length === 0) {
                        setNoGetPaidHistoryMessage({isError: false, message: "You have no history or active analyses for improving."});
                    }
                }
                else {
                    if (data.notLoggedIn) {
                        if (account.loggedIn) {
                            dispatch(setLoggedIn(false));
                            dispatch(setAccount({ username: "" }));
                        }
                        
                        setGetPaidHistory([]);
                        setNoGetPaidHistoryMessage({isError: true, message: "You are not logged in. Please log in to view your history or active analyses for improving."});
                    }
                    else {
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
                    {account?.loggedIn && (
                        <div className="mb-16 lg:mb-20 w-fit mx-auto flex items-center gap-4">
                            <div className="hidden">
                                <InfoButton infoText={INFO_GET_PAID_PAGE}/>
                            </div>
                            <button className="--button button--success" onClick={handleRequestAnalysisForImprovingClick}>Request Analysis For Improving</button>
                            <InfoButton infoText={INFO_GET_PAID_PAGE}/>
                        </div>
                    )}
                    
                    {getPaidHistory.length > 0 ? (
                        <>
                            <hr className="container px-6 lg:px-0 mx-auto bg-primary/20 h-0.5"/>

                            <div className="container px-6 lg:px-0 mx-auto mt-16 lg:mt-20">
                                {getPaidHistory.map((improvement, index) => (
                                    <div key={index}
                                         className={`${improvement.status !== 'IN_PROGRESS' ? 'opacity-80 bg-gray-500/10' : 'cursor-pointer bg-primary/10'} px-8 py-5 lg:py-3 grid grid-cols-1 lg:grid-cols-9 w-full lg:justify-between gap-4 lg:gap-6 rounded-xl border-2 border-white transition-all duration-300 hover:bg-primary hover:text-white hover:shadow-md`}
                                         onClick={(e) => improvement.status === 'IN_PROGRESS' ? handleImproveTranscriptionClick(e, improvement.id) : null}
                                    >
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Filename (By):</p>
                                            <p className="!leading-5 heading--6 flex items-end gap-4">{improvement?.improvementRequest?.transcription?.filename} (by {improvement?.improvementRequest?.employer?.username}) <span className={`${improvement.status !== 'finished' ? 'hidden' : 'inline lg:hidden'}`}>→</span></p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Improvement Status:</p>
                                            <p className="!leading-5 heading--6">{improvement.status}</p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Date Created:</p>
                                            <p className="!leading-5 heading--6">{new Date(improvement.timestampCreated).toLocaleString()}</p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Date Updated:</p>
                                            <p className="!leading-5 heading--6">{new Date(improvement.timestampUpdated).toLocaleString()}</p>
                                        </div>
                                        {improvement.status === 'IN_PROGRESS' && (
                                            <div className="flex flex-col overflow-hidden justify-center lg:items-end">
                                                <p className="!leading-5 heading--5">Improve →</p>
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

                    <div className="py-10"></div>
                </>
            )}
        </div>
    )

    function handleGoBackHome(e) {
        navigate("/");
    }

    function handleRequestAnalysisForImprovingClick(e) {
        setIsLoading(true);

        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        axios.get(`${serverApiUrl}/improvements/requestAnalysisForImproving`, {
            headers: headers
        })
            .then(response => {
                setIsLoading(false);

                const data = response?.data;

                if (!data?.error) {
                    setGetPaidHistory(data.improvementsHistory);

                    dispatch(setTextSuccess("Successfully requested a new analysis for improving."));
                }
                else {
                    if (data.notLoggedIn) {
                        if (account.loggedIn) {
                            dispatch(setLoggedIn(false));
                            dispatch(setAccount({ username: "" }));

                            dispatch(setTextError("You are not logged in. Please log in to request analysis for improving."));
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
            })
    }

    function handleImproveTranscriptionClick(e, improvementResponseId) {
        setIsLoading(true);

        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        axios.get(`${serverApiUrl}/improvements/enterIsImprovingMode/${improvementResponseId}`, {
            headers: headers
        })
            .then(response => {
                setIsLoading(false);

                const data = response?.data;

                if (!data?.error) {
                    dispatch(setAnalysisResult(data?.improvementResponse?.improvementRequest?.transcription));

                    dispatch(setAnalysisEmployeeInfo({
                        improvemenResponsetId: data?.improvementResponse?.id,
                        isImproving: true,
                        originalText: data?.improvementResponse?.oldTranscriptionText,
                        employer: data?.improvementResponse?.improvementRequest?.employer?.username,
                    }))

                    navigate("/analysis");
                }
                else {
                    if (data.notLoggedIn) {
                        if (account.loggedIn) {
                            dispatch(setLoggedIn(false));
                            dispatch(setAccount({ username: "" }));

                            dispatch(setTextError("You are not logged in. Please log in to improve this analysis."));
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
            })
    }
}

export default GetPaidPage