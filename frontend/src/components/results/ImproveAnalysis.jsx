import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';

import axios from 'axios'

import InfoButton from '../utils/InfoButton';
import { INFO_ANALYSIS_IS_BEING_IMPROVED, INFO_IMPROVE_THIS_ANALYSIS, INFO_YOU_ARE_CURRENTLY_IMPROVING_THIS_ANALYSIS } from '../../utils/infoTexts';
import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice';
import { setAnalysisImprovementInfo } from '../../redux/reducers/analysisResultSlice';
import { serverApiUrl } from '../../utils/envVariables';
import { getCookie } from '../../utils/functions/cookies';
import { setTextError } from '../../redux/reducers/errorsSlice';

const ImproveAnalysis = () => {
    const dispatch = useDispatch();

    const [isLoading, setIsLoading] = useState(false);

    const analysis = useSelector(state => state?.analysisResult?.result);
    const improvementInfo = useSelector(state => state?.analysisResult?.improvementInfo);
    const employeeInfo = useSelector(state => state?.analysisResult?.employeeInfo);

    const account = useSelector(state => state?.account)

    return (
        <>
            {account?.loggedIn ? (
                <div className="mb-16 w-fit mx-auto flex items-center gap-4">
                    {!isLoading ? (
                        <>
                            {employeeInfo?.isImproving ? (
                                <>
                                    <div className="text-center">
                                        <p className="mb-2">You are currently improving this analysis.</p>
                                        <div className="flex flex-col lg:flex-row lg:items-center gap-4">
                                            <button className="--button-small button--success" onClick={(e) => handleEmployeeSyncChanges(e, analysis.id)}>Sync Changes</button>
                                            <button className="--button-small button--error" onClick={(e) => handleEmployeeRevertToOriginal(e)}>Revert To Original</button>
                                            <button className="--button-small button--success" onClick={(e) => handleEmployeeFinishImproving(e, analysis.id)}>Finish Improving</button>
                                            <div className="flex-shrink-0 w-fit mx-auto">
                                                <InfoButton infoText={INFO_YOU_ARE_CURRENTLY_IMPROVING_THIS_ANALYSIS}/>
                                            </div>
                                        </div>
                                    </div>
                                </>
                            ) : (
                                <>
                                    {!improvementInfo?.isRequested ? (
                                        <>
                                            <button className="--button button--success" onClick={(e) => handleImproveRequest(e, analysis.id)}>Improve Your Analysis</button>
                                            <InfoButton infoText={INFO_IMPROVE_THIS_ANALYSIS.replace(/\[\[\[PRICE\]\]\]/g, improvementInfo?.cost ?? '-')}/>
                                        </>
                                    ) : (
                                        <div className="text-center">
                                            <p className="mb-2">Your analysis is currently being improved.</p>
                                            <div className="flex items-center gap-4">
                                                <button className="--button-small button--error" onClick={(e) => handleFinishImprovements(e, improvementInfo?.improvementRequest?.id)}>Finish Improvements Now</button>
                                                <InfoButton infoText={INFO_ANALYSIS_IS_BEING_IMPROVED
                                                                        .replace(/\[\[\[IMPROVED_BY\]\]\]/g, improvementInfo?.improvedBy ?? '-')
                                                                        .replace(/\[\[\[DEADLINE\]\]\]/g, improvementInfo?.deadline?.toLocaleString() ?? '-')}/>
                                            </div>
                                        </div>
                                    )}
                                </>
                            )}
                        </>
                    ) : (
                        <>
                            {employeeInfo.isImproving ? (
                                <>
                                </>
                            ) : (
                                <>
                                    <img src="/images/icon-spinner.png" className="w-12 animate-spin" alt="icon spinner"/>
                                </>
                            )}
                        </>
                    )}
                </div>
            ) : (
                <div className="mb-16 w-fit mx-auto flex items-center gap-4">
                    <p className="heading--5 text-center">Log in to improve your analysis.</p>
                </div>
            )}
        </>
    )

    function handleImproveRequest(e, transcriptionId) {
        setIsLoading(true);

        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        axios.post(`${serverApiUrl}/improvements/requestImprovement/${transcriptionId}`, {}, {
            headers: headers
        })
            .then(response => {
                setIsLoading(false);

                const data = response?.data;

                if (!data?.error) {
                    dispatch(setAnalysisImprovementInfo({
                        isRequested: true,
                        improvedBy: data?.improvementRequest?.improvedByCount,
                        deadline: new Date(data?.improvementRequest?.timestampCreated).setDate(new Date(data?.improvementRequest?.timestampCreated).getDate() + 7)
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

    function handleFinishImprovements(e, transcriptionId) {
        
    }

    function handleEmployeeSyncChanges(e, id) {

    }

    function handleEmployeeFinishImproving(e, id) {

    }

    function handleEmployeeRevertToOriginal(e) {
        let transcriptionTextGlobalInput = document.getElementById("transcriptionTextGlobal");

        if (transcriptionTextGlobalInput && employeeInfo?.originalText) {
            transcriptionTextGlobalInput.value = JSON.parse(employeeInfo?.originalText)["Global_text"];
        }
    }
}

export default ImproveAnalysis