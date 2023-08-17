import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux';

import InfoButton from '../utils/InfoButton'
import { INFO_DO_NOT_REFRESH_PAGE } from '../../utils/infoTexts'
import { setPageContent } from '../../redux/reducers/pageContentSlice';
import { setAnalysisResult } from '../../redux/reducers/analysisResultSlice';
import { TEST_ANALYSIS_RESULT_RESPONSE } from '../../utils/testingData';

const AudioProcessingPage = () => {
    const dispatch = useDispatch();
    
    // serverStatus: 0 - server is receiving the audio file;
    //               1 - server is processing the audio file;
    //               2 - server has finished processing the audio file;
    //               3 - server has encountered an error while processing the audio file;
    const [serverStatus, setServerStatus] = useState(0);

    useEffect(() => {
        // change serverStatus to 1 after 500 milliseconds
        setTimeout(() => {
            setServerStatus(1);

            // TODO: replace this with real audio processing id
            // it will help to restore the audio processing page if user refreshes the page
            localStorage.setItem('audioProcessingId', 12304);
        }, 500);

        // change serverStatus to 2 after 1 second
        setTimeout(() => {
            setServerStatus(2);

            // Save result in redux store
            dispatch(setAnalysisResult(TEST_ANALYSIS_RESULT_RESPONSE))

            // Set page content to results page
            dispatch(setPageContent(2));
        }, 1000);
    }, []);

    return (
        <div className="px-6 lg:px-0">
            <div className="flex justify-center mb-6">
                {
                    serverStatus !== 3 ? (
                        <img src="/images/icon-spinner.png" className="w-12 animate-spin" alt="icon spinner"/>
                    ) : (
                        <p className="opacity-60 text-5xl">❌</p>
                    )
                }
            </div>
            
            {
                serverStatus === 0 ? (
                    <p className="heading--3 text-center">The server is receiving the Audio File</p>
                ) : serverStatus === 1 ? (
                    <p className="heading--3 text-center">The server is processing the Audio File</p>
                ) : serverStatus === 3 ? (
                    <>
                        <p className="heading--3 text-center">The server has encountered an error<br/>while processing the Audio File</p>
                        <button type="button" className="--button button--primary w-fit mx-auto mt-12" onClick={restoreHomepageToTryAgain}>Try Again</button>
                    </>
                ) : (<></>)
            }

            {serverStatus === 0 && (
                <div className="--small-text text-center mt-2 flex justify-center items-center gap-2">
                    Please do not refresh the page
                    <InfoButton infoText={INFO_DO_NOT_REFRESH_PAGE}/>
                </div>
                )}
        </div>
    )

    function restoreHomepageToTryAgain(e) {
        // Set page content to homepage
        dispatch(setPageContent(0));
    }
}

export default AudioProcessingPage