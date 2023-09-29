import React from 'react'
import { useSelector } from 'react-redux'

import AudioFileDropZone from './AudioFileDropZone'
import InfoButton from '../utils/InfoButton'
import { INFO_FREE_VERSION } from '../../utils/infoTexts'

const Homepage = () => {
    const account = useSelector(state => state?.account)

    return (
        <div className="px-6 lg:px-0">
            <h1 className="mt-2 heading--3 text-center">Talk Analysis & Sound Extraction Resource</h1>

            <div className="--small-text text-center mt-2 mb-16 flex justify-center items-center gap-2">
                Free Version TASER-F
                <InfoButton infoText={INFO_FREE_VERSION}/>
            </div>

            {account?.loggedIn && account?.account?.username && (
                <p className="mb-4 heading--3 text-center text-primary">Welcome, {account.account?.username}!</p>
            )}

            <AudioFileDropZone/>

            <div className="py-20"></div>
        </div>
    )
}

export default Homepage