import { configureStore } from '@reduxjs/toolkit'

import infoButtonReducer from './reducers/infoButtonSlice'
import pageContentReducer from './reducers/pageContentSlice'
import analysisResultReducer from './reducers/analysisResultSlice'
import shownAnalysisReducer from './reducers/shownAnalysisSlice'


export default configureStore({
  reducer: {
    infoButton: infoButtonReducer,
    pageContent: pageContentReducer,
    analysisResult: analysisResultReducer,
    shownAnalysis: shownAnalysisReducer,
  },
})