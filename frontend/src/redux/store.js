import { configureStore } from '@reduxjs/toolkit'

import infoButtonReducer from './reducers/infoButtonSlice'
import pageContentReducer from './reducers/pageContentSlice'
import analysisResultReducer from './reducers/analysisResultSlice'


export default configureStore({
  reducer: {
    infoButton: infoButtonReducer,
    pageContent: pageContentReducer,
    analysisResult: analysisResultReducer,
  },
})