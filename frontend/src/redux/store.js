import { configureStore } from '@reduxjs/toolkit'

import infoButtonReducer from './reducers/infoButtonSlice'
import analysisResultReducer from './reducers/analysisResultSlice'
import shownAnalysisReducer from './reducers/shownAnalysisSlice'


export default configureStore({
  reducer: {
    infoButton: infoButtonReducer,
    analysisResult: analysisResultReducer,
    shownAnalysis: shownAnalysisReducer,
  },
})