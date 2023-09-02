import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import axios from 'axios'
import { serverApiUrl } from '../../utils/envVariables'

import { getCookie } from '../../utils/functions/cookies';
import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice';
import { setAnalysisResult } from '../../redux/reducers/analysisResultSlice';

const HistoryPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    
    const account = useSelector(state => state?.account)

    const [history, setHistory] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [noHistoryMessage, setNoHistoryMessage] = useState({
        isError: false,
        message: "You have no history of past analyses."
    });

    useEffect(() => {
        if (!account.loggedIn) {
            setIsLoading(false);
            setHistory([]);
            setNoHistoryMessage({isError: true, message: "You are not logged in. Please log in to view your history."});
            return;
        }
        
        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        axios.get(`${serverApiUrl}/history`, {
            headers: headers
        })
            .then(response => {
                const data = response?.data;
                if (!data?.error) {
                    setHistory(data.transcriptionHistory);
                    setIsLoading(false);
                }
                else {
                    if (data.notLoggedIn) {
                        if (account.loggedIn) {
                            dispatch(setLoggedIn(false));
                            dispatch(setAccount({ username: "" }));
                        }
                        
                        setIsLoading(false);
                        setHistory([]);
                        setNoHistoryMessage({isError: true, message: "You are not logged in. Please log in to view your history."});
                    }
                    else {
                        setIsLoading(false);
                        setHistory([]);
                        setNoHistoryMessage({isError: true, message: data.message});
                    }
                }
            })
            .catch(error => {
                setIsLoading(false);
                setHistory([]);
                setNoHistoryMessage({isError: true, message: "Unknown error. Please try again later."});
            })

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [account.loggedIn])

    return (
        <div className="px-6 lg:px-0">
            <p className="heading--3 text-center">History - Your Past Analyses</p>

            <div className="mt-16">
                {isLoading ? (
                    <div className="flex justify-center items-center gap-2">
                        <img src="/images/icon-spinner.png" className="w-8 animate-spin" alt="icon spinner"/>
                        <p className="heading--6 text-center">Loading...</p>
                    </div>
                ) : (
                    <>
                        {history.length > 0 ? (
                            <div className="flex flex-col gap-6 lg:px-6">
                                {history.map((transcription, index) => (
                                    <div key={index}
                                         className="px-8 py-5 lg:py-3 grid grid-cols-1 lg:grid-cols-9 w-full lg:justify-between gap-4 lg:gap-6 rounded-xl bg-primary/10 border-2 border-white cursor-pointer transition-all duration-300 hover:bg-primary hover:text-white hover:shadow-md"
                                         onClick={(e) => handleTranscriptionClick(e, transcription.id)}     
                                    >
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Filename:</p>
                                            <p className="!leading-5 heading--6 flex items-end gap-4">{transcription.filename} <span className="inline lg:hidden">→</span></p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Analysis Completed:</p>
                                            <p className="!leading-5 heading--6">{transcription.isCompleted ? '✅' : '❌'}</p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Date Created:</p>
                                            <p className="!leading-5 heading--6">{new Date(transcription.timestampCreated).toLocaleString()}</p>
                                        </div>
                                        <div className="flex flex-col overflow-hidden lg:col-span-2">
                                            <p className="!leading-5 --small-text">Date Updated:</p>
                                            <p className="!leading-5 heading--6">{new Date(transcription.timestampUpdated).toLocaleString()}</p>
                                        </div>
                                        <div className="hidden lg:flex flex-col overflow-hidden justify-center items-end">
                                            <p className="heading--3">→</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <>
                                <p className={`heading--6 text-center ${noHistoryMessage.isError ? 'text-red-700' : ''}`}>{noHistoryMessage.isError ? '❌' : ''} {noHistoryMessage.message}</p>
                                <div className="mt-2 flex flex-col lg:flex-row gap-4 lg:gap-8 justify-center items-center">
                                    <button className="--button button--primary" onClick={handleGoBackHome}>← Go Back Home</button>
                                </div>
                            </>
                        )}
                    </>
                )}
            </div>
        </div>
    )

    function handleGoBackHome(e) {
        navigate("/");
    }

    function handleTranscriptionClick(e, transcriptionId) {
        setIsLoading(true);

        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        axios.get(`${serverApiUrl}/transcription/${transcriptionId}`, {
            headers: headers
        })
            .then(response => {
                const data = response?.data;
                if (!data?.error) {
                    dispatch(setAnalysisResult(data.transcription));
                    navigate("/analysis");
                }
                else {
                    if (data.notLoggedIn) {
                        if (account.loggedIn) {
                            dispatch(setLoggedIn(false));
                            dispatch(setAccount({ username: "" }));
                        }
                        
                        setIsLoading(false);
                        setHistory([]);
                        setNoHistoryMessage({isError: true, message: "You are not logged in. Please log in to view your history."});
                    }
                    else {
                        setIsLoading(false);
                        setHistory([]);
                        setNoHistoryMessage({isError: true, message: data.message});
                    }
                }
            })
            .catch(error => {
                setIsLoading(false);
                setHistory([]);
                setNoHistoryMessage({isError: true, message: "Unknown error. Please try again later."});
            })
    }
}

export default HistoryPage