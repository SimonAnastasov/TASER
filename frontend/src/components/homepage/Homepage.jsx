import React from 'react'
import AudioFileDropZone from './AudioFileDropZone'
import InfoButton from '../utils/InfoButton'
import { INFO_FREE_VERSION } from '../../utils/infoTexts'

const Homepage = () => {
    return (
        <div>
            <div className="tailwindClassesLoader hidden"></div>

            <h1 className="heading--3 text-center">Talk Analysis & Sound Extraction Resource</h1>
            <div className="--small-text text-center mt-6 flex justify-center items-center gap-2">
                Free Version TASER-F
                <InfoButton infoText={INFO_FREE_VERSION}/>
            </div>

            <AudioFileDropZone/>
        </div>
    )
}

export default Homepage