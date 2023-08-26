import React from 'react'

import { Routes, Route } from 'react-router-dom';

import Homepage from '../homepage/Homepage'
import AudioProcessingPage from '../audioProcessing/AudioProcessingPage'
import ResultsPage from '../results/ResultsPage'

const PageContent = () => {
    return (
        <Routes>
            <Route exact path='/' element={< Homepage />}></Route>
            <Route exact path='/processing' element={< AudioProcessingPage />}></Route>
            <Route exact path='/analysis' element={< ResultsPage />}></Route>
        </Routes>
    )
}

export default PageContent