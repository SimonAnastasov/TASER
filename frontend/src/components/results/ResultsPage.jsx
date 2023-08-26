import React from 'react'
import { useSelector } from 'react-redux'

import GlobalAnalysisResults from './GlobalAnalysisResults'
import SegmentBySegmentAnalysisResults from './SegmentBySegmentAnalysisResults'
import ToggleShownAnalysis from './ToggleShownAnalysis'

import InfoButton from '../utils/InfoButton'
import { INFO_RESULTS_PAGE } from '../../utils/infoTexts'

const ResultsPage = () => {
    const analysisResult = useSelector(state => state?.analysisResult?.result)
    const shownAnalysis = useSelector(state => state?.shownAnalysis?.shownAnalysis)

    return (
        <div className="px-6 lg:px-0">
            {analysisResult ? (
                <>
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
                <p className="heading--3 text-center">No results to be shown.</p>
            )}
        </div>
    )
}

export default ResultsPage