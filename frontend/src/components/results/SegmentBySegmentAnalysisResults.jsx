import React, { useState } from 'react'
import { useSelector } from 'react-redux'

import SentimentIcon from '../utils/SentimentIcon';
import InfoButton from '../utils/InfoButton';
import InfoBoxes from './InfoBoxes';

const SegmentBySegmentAnalysisResults = () => {
    const analysisResult = useSelector(state => state?.analysisResult?.result)
    const totalSegments = analysisResult.segments.length;

    const [shownSegmentNumber, setShownSegmentNumber] = useState(0)
    

    if (!analysisResult || Object.keys(analysisResult).length === 0) return <></>;

    return (
        <>
            {analysisResult.segments.map((segment, index) => (
                <div key={`segment${index}`} className={`${index === shownSegmentNumber ? '' : 'hidden'}`}>
                    {/* Switch between segments with back and forward arrows */}
                    <div className="mt-12 lg:mt-16 lg:px-6 w-fit mx-auto flex justify-center items-center gap-4 lg:gap-12">
                        <div className="text-2xl w-fit py-2 px-4 rounded shadow-md bg-white text-black cursor-pointer hover:bg-black hover:text-white transition-all duration-300"
                             onClick={() => setShownSegmentNumber( (totalSegments + shownSegmentNumber - 1) % totalSegments )}
                        >
                            ←
                        </div>
                        <div className="">
                            Segment {index + 1}/{totalSegments}
                        </div>
                        <div className="text-2xl w-fit py-2 px-4 rounded shadow-md bg-white text-black cursor-pointer hover:bg-black hover:text-white transition-all duration-300"
                             onClick={() => setShownSegmentNumber( (totalSegments + shownSegmentNumber + 1) % totalSegments )}
                        >
                            →
                        </div>
                    </div>

                    {/* Show speaker */}
                    <div className="mt-8 flex gap-2 justify-center items-center">
                        <span className="heading--6 text-center">Speaker: <span className="font-bold">{segment.speaker}</span></span>
                    </div>

                    {/* Show time frame of speaking */}
                    <div className="mt-2 w-fit mx-auto">
                        <div className="flex justify-center items-center gap-4">
                            <div className="flex flex-col items-center gap-1">
                                <InfoButton infoText={"The timestamp in the audio when the speaker in this segment started speaking."} small/>
                                <div className="heading--3">🎬</div>
                                <div className="heading--6">{segment.start.toFixed(2)}s</div>
                            </div>
                            <div className="h-0.5 w-8 lg:w-32 bg-black -translate-y-3"></div>
                            <div className="flex flex-col items-center gap-1">
                                <InfoButton infoText={"The total duration for which the speaker in this segment was speaking."} small/>
                                <div className="heading--3">⏳</div>
                                <div className="heading--6">{(segment.end - segment.start).toFixed(2)}s</div>
                            </div>
                            <div className="h-0.5 w-8 lg:w-32 bg-black -translate-y-3"></div>
                            <div className="flex flex-col items-center gap-1">
                                <InfoButton infoText={"The timestamp in the audio when the speaker in this segment finished speaking."} small/>
                                <div className="heading--3">🏁</div>
                                <div className="heading--6">{segment.end.toFixed(2)}s</div>
                            </div>
                        </div>
                    </div>

                    {/* Show sentiment */}
                    <div className="mt-2 flex gap-2 justify-center items-center">
                        <SentimentIcon sentiment={segment.sentiment}/>
                        <span className="heading--6">Sentiment: {segment.sentiment[0].toUpperCase() + segment.sentiment.slice(1)}</span>
                        <SentimentIcon sentiment={segment.sentiment}/>
                    </div>

                    {/* Show info boxes */}
                    <InfoBoxes  entities_list={segment.entities} 
                                global_texts={[
                                    {title: "Transcription Text", content: segment.text},
                                    {title: "Abstractive Summarization", content: segment.abstractive_summarization_text}
                                ]}
                    />
                </div>
            ))}
        </>
    )
}

export default SegmentBySegmentAnalysisResults