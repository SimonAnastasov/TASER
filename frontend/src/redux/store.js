import { configureStore } from '@reduxjs/toolkit'

import infoButtonReducer from './reducers/infoButtonSlice'
import analysisResultReducer from './reducers/analysisResultSlice'
import shownAnalysisReducer from './reducers/shownAnalysisSlice'

import accountReducer from './reducers/accountSlice'


export default configureStore({
  reducer: {
    infoButton: infoButtonReducer,
    analysisResult: analysisResultReducer,
    shownAnalysis: shownAnalysisReducer,
    
    account: accountReducer,
  },
})