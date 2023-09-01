import { configureStore } from '@reduxjs/toolkit'

import infoButtonReducer from './reducers/infoButtonSlice'
import audioProcessingReducer from './reducers/audioProcessingSlice'
import analysisResultReducer from './reducers/analysisResultSlice'
import shownAnalysisReducer from './reducers/shownAnalysisSlice'

import accountReducer from './reducers/accountSlice'


export default configureStore({
  reducer: {
    infoButton: infoButtonReducer,
    audioProcessing: audioProcessingReducer,
    analysisResult: analysisResultReducer,
    shownAnalysis: shownAnalysisReducer,
    
    account: accountReducer,
  },
})