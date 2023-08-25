import React, { useState } from 'react'
import { useSelector } from 'react-redux'

import GlobalAnalysisResults from './GlobalAnalysisResults'
import SegmentBySegmentAnalysisResults from './SegmentBySegmentAnalysisResults'

import InfoButton from '../utils/InfoButton'
import { INFO_RESULTS_PAGE } from '../../utils/infoTexts'

const ResultsPage = () => {
    const analysisResult = useSelector(state => state?.analysisResult?.result)

    
    // 0 for global analysis
    // 1 for segment by segment analysis
    const [shownAnalysisToggle, setShownAnalysisToggle] = useState(0);

    return (
        <div className="px-6 lg:px-0">
            {analysisResult ? (
                <>
                    <div className="mb-16 w-fit mx-auto grid grid-cols-2 border border-white shadow-md rounded-full">
                        <div className={`py-2 px-4 rounded-l-full text-center text-sm lg:text-lg flex justify-center items-center transition-all duration-300 ${shownAnalysisToggle === 0 ? 'bg-primary text-white' : 'bg-white/50 cursor-pointer hover:bg-primary hover:text-white'}`} onClick={() => setShownAnalysisToggle(0)}>Global Analysis 🌐</div>
                        <div className={`py-2 px-4 rounded-r-full text-center text-sm lg:text-lg flex justify-center items-center transition-all duration-300 ${shownAnalysisToggle === 1 ? 'bg-dark text-white' : 'bg-white/50 cursor-pointer hover:bg-dark hover:text-white'}`} onClick={() => setShownAnalysisToggle(1)}>Segment By Segment Analysis 📑</div>
                    </div>

                    <div className="flex justify-center items-center">
                        <InfoButton infoText={INFO_RESULTS_PAGE}/>
                    </div>

                    {
                        shownAnalysisToggle === 0 ? (
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