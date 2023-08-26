import React, { useState } from 'react'
import { useSelector } from 'react-redux'

const SegmentBySegmentAnalysisResults = () => {
    const analysisResult = useSelector(state => state?.analysisResult?.result)

    const [shownSegmentNumber, setShownSegmentNumber] = useState(0)
    

    if (!analysisResult) return <></>;

    return (
        <>
            {analysisResult.segments.map((segment, index) => (
                <div key={`segment${index}`} className={`${index === shownSegmentNumber ? '' : 'hidden'}`}>
                    <div className="lg:px-6 mx-auto mt-8 grid grid-cols-1 lg:grid-cols-3 gap-y-8 lg:gap-8">
                        Test
                    </div>
                </div>
            ))}
        </>
    )
}

export default SegmentBySegmentAnalysisResults