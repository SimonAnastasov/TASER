import React from 'react'
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import InfoButton from '../utils/InfoButton'
import { INFO_DO_NOT_REFRESH_PAGE } from '../../utils/infoTexts'

const AudioProcessingPage = () => {
    const navigate = useNavigate();

    const audioProcessing = useSelector(state => state?.audioProcessing);

    return (
        <div className="px-6 lg:px-0">
            <div className="flex justify-center mb-6">
                {
                    audioProcessing.audioProcessingStatus === 3 ? (
                        <p className="opacity-60 text-5xl">❌</p>
                    ) : audioProcessing.audioProcessingStatus === 2 ? (
                        <></>
                    ) : (
                        <img src="/images/icon-spinner.png" className="w-12 animate-spin" alt="icon spinner"/>
                    )
                }
            </div>
            
            {
                audioProcessing.audioProcessingStatus === 0 ? (
                    <p className="heading--3 text-center">The server is receiving the Audio File</p>
                ) : audioProcessing.audioProcessingStatus === 1 ? (
                    <p className="heading--3 text-center">The server is processing the Audio File</p>
                ) : audioProcessing.audioProcessingStatus === 2 ? (
                    <>
                        <p className="heading--3 text-center">The server has finished processing the Audio File</p>
                        <div className="mt-8 flex flex-col lg:flex-row gap-4 lg:gap-8 justify-center items-center">
                            <button className="--button button--primary" onClick={handleGoBackHome}>← Go Back Home</button>
                            <button className="--button button--primary" onClick={handleViewAnalysis}>View Analysis →</button>
                        </div>    
                    </>
                ) : audioProcessing.audioProcessingStatus === 3 ? (
                    <>
                        <p className="heading--3 text-center">The server has encountered an error<br/>while processing the Audio File</p>
                        <button type="button" className="--button button--primary w-fit mx-auto mt-12" onClick={handleGoBackHome}>Try Again</button>
                    </>
                ) : (<></>)
            }

            {/* Ideally not refreshing should be only recommended when server is receiving audio file (audioProcessingStatus = 0) */}
            {/* This can be done if we have async enabled in the backend */}
            {audioProcessing.audioProcessingStatus === 1 && (
                <div className="--small-text text-center mt-2 flex justify-center items-center gap-2">
                    Please do not refresh the page
                    <InfoButton infoText={INFO_DO_NOT_REFRESH_PAGE}/>
                </div>
                )}
        </div>
    )

    function handleGoBackHome(e) {
        navigate("/");
    }

    function handleViewAnalysis(e) {
        navigate("/analysis");
    }
}

export default AudioProcessingPage