import React from 'react'
import { useSelector } from 'react-redux'

import Homepage from '../homepage/Homepage'
import AudioProcessingPage from '../audioProcessing/AudioProcessingPage'

const PageContent = () => {
    const pageContent = useSelector(state => state.pageContent.content)

    return (
        <>
            {
                pageContent === 0 ? (
                    <Homepage/>
                ) : pageContent === 1 ? (
                    <AudioProcessingPage/>
                ) : pageContent === 2 ? (
                    <></>
                ) : (<></>)
            }
        </>
    )
}

export default PageContent