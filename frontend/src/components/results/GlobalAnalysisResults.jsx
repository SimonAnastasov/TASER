import React from 'react'
import { useSelector } from 'react-redux'

import SentimentIcon from '../utils/SentimentIcon'
import InfoBoxes from './InfoBoxes'

const GlobalAnalysisResults = () => {
    const analysis = useSelector(state => state?.analysisResult?.result)
    const analysisResult = analysis?.id ? JSON.parse(analysis?.text) : null;

    const global_texts = [
        {title: "Transcription Text", content: analysisResult?.Global_text},
        {title: "Abstractive Summarization", content: analysisResult?.Global_abstractive_summarization}
    ]

    
    if (!analysisResult || Object.keys(analysisResult).length === 0) return <></>;

    return (
        <>
            {/* Show global sentiment */}
            <div className="mt-4 flex gap-2 justify-center items-center">
                <SentimentIcon sentiment={analysisResult.Global_sentiment}/>
                <span className="heading--5">Sentiment: {analysisResult.Global_sentiment[0].toUpperCase() + analysisResult.Global_sentiment.slice(1)}</span>
                <SentimentIcon sentiment={analysisResult.Global_sentiment}/>
            </div>

            {/* Show info boxes */}
            <InfoBoxes entities_list={analysisResult.Global_entities_list} global_texts={global_texts}/>
        </>
    )
}

export default GlobalAnalysisResults