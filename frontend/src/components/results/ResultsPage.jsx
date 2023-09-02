import React from 'react'
import { useSelector } from 'react-redux'

import GlobalAnalysisResults from './GlobalAnalysisResults'
import SegmentBySegmentAnalysisResults from './SegmentBySegmentAnalysisResults'
import ToggleShownAnalysis from './ToggleShownAnalysis'

import InfoButton from '../utils/InfoButton'
import { INFO_RESULTS_PAGE } from '../../utils/infoTexts'
import { useNavigate } from 'react-router-dom'

const ResultsPage = () => {
    const navigate = useNavigate();
    
    const analysis = useSelector(state => state?.analysisResult?.result)
    const analysisResult = analysis?.id ? JSON.parse(analysis?.text) : null;

    const shownAnalysis = useSelector(state => state?.shownAnalysis?.shownAnalysis)

    return (
        <div className="px-6 lg:px-0">
            {analysisResult && Object.keys(analysisResult).length > 0 ? (
                <>
                    <div className="mb-12">
                        <p className="--small-text text-center">Filename:</p>
                        <p className="heading--6 text-center">{analysis.filename}</p>
                    </div>

                    <div className="mb-8 lg:mb-16 w-fit mx-auto text-sm lg:text-lg">
                        <ToggleShownAnalysis/>
                    </div>

                    <div className="flex justify-center items-center">
                        <InfoButton infoText={INFO_RESULTS_PAGE}/>
                    </div>

                    {
                        shownAnalysis === 0 ? (
                            <GlobalAnalysisResults/>
                        ) : (
                            <SegmentBySegmentAnalysisResults/>
                        )
                    }

                    <div className="py-20"></div>
                </>
            ) : (
                <>
                    <p className="heading--3 text-center">No analysis to be shown.</p>
                    <div className="mt-8 flex flex-col lg:flex-row gap-4 lg:gap-8 justify-center items-center">
                        <button className="--button button--primary" onClick={handleGoBackHome}>← Go Back Home</button>
                        <button className="--button button--primary" onClick={handleViewPastAnalyses}>View Past Analyses →</button>
                    </div>
                </>
            )}
        </div>
    )

    function handleGoBackHome(e) {
        navigate("/");
    }

    function handleViewPastAnalyses(e) {
        navigate("/history");
    }
}

export default ResultsPage