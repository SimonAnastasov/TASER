import React from 'react'

import { Routes, Route } from 'react-router-dom';

import Homepage from '../homepage/Homepage'
import AudioProcessingPage from '../audioProcessing/AudioProcessingPage'
import ResultsPage from '../results/ResultsPage'

import LogIn from '../accounts/LogIn';
import SignUp from '../accounts/SignUp';
import HistoryPage from '../history/HistoryPage';

const PageContent = () => {
    return (
        <Routes>
            <Route exact path='/' element={<Homepage/>}></Route>
            <Route exact path='/processing' element={<AudioProcessingPage/>}></Route>
            <Route exact path='/analysis' element={<ResultsPage/>}></Route>

            <Route exact path='/history' element={<HistoryPage/>}></Route>

            <Route exact path='/login' element={<LogIn/>}></Route>
            <Route exact path='/signup' element={<SignUp/>}></Route>
        </Routes>
    )
}

export default PageContent