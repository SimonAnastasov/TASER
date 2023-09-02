import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';

import axios from 'axios'
import { serverApiUrl } from '../../utils/envVariables'

import { getCookie } from '../../utils/functions/cookies';

const HistoryPage = () => {
    const navigate = useNavigate();
    
    const [history, setHistory] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [noHistoryMessage, setNoHistoryMessage] = useState("You have no history of past analyses.");

    useEffect(() => {
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
                    setHistory(data);
                    setIsLoading(false);
                }
                else {
                    setHistory([]);
                    setNoHistoryMessage("Unknown error. Please try again later.");
                }
            })
            .catch(error => {
                setHistory([]);
                setNoHistoryMessage("Unknown error. Please try again later.");
            })
    }, [])

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
                            <>

                            </>
                        ) : (
                            <>
                                <p className="heading--6 text-center">{noHistoryMessage}</p>
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
}

export default HistoryPage