import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';

import axios from 'axios'

import InfoButton from '../utils/InfoButton';
import { INFO_ANALYSIS_IS_BEING_IMPROVED, INFO_IMPROVE_THIS_ANALYSIS } from '../../utils/infoTexts';

const ImproveAnalysis = () => {
    const dispatch = useDispatch();

    const analysis = useSelector(state => state?.analysisResult?.result);

    const account = useSelector(state => state?.account)

    return (
        <>
            {account?.loggedIn ? (
                <div className="mb-16 w-fit mx-auto flex items-center gap-4">
                    {!analysis?.improvmentInfo?.isBeingEdited ? (
                        <>
                            <button className="--button button--success" onClick={(e) => handleImproveRequest(e, analysis.id)}>Improve Your Analysis</button>
                            <InfoButton infoText={INFO_IMPROVE_THIS_ANALYSIS.replace(/\[\[\[PRICE\]\]\]/g, analysis?.improvementInfo?.cost ?? '-')}/>
                        </>
                    ) : (
                        <div className="text-center">
                            <p className="mb-2">Your analysis is currently being improved.</p>
                            <div className="flex items-center gap-4">
                                <button className="--button-small button--error">Finish Improvements Now</button>
                                <InfoButton infoText={INFO_ANALYSIS_IS_BEING_IMPROVED
                                                        .replace(/\[\[\[IMPROVED_BY\]\]\]/g, analysis?.improvmentInfo?.improvedBy ?? '-')
                                                        .replace(/\[\[\[DEADLINE\]\]\]/g, analysis?.improvmentInfo?.deadline?.toLocaleString() ?? '-')}/>
                            </div>
                        </div>
                    )}
                </div>
            ) : (
                <div className="mb-16 w-fit mx-auto flex items-center gap-4">
                    <p className="heading--5 text-center">Log in to improve your analysis.</p>
                </div>
            )}
        </>
    )

    function handleImproveRequest(e, analysisId) {
        
    }
}

export default ImproveAnalysis