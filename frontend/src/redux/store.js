import { configureStore } from '@reduxjs/toolkit'

import infoButtonReducer from './reducers/infoButtonSlice'
import pageContentReducer from './reducers/pageContentSlice'

export default configureStore({
  reducer: {
    infoButton: infoButtonReducer,
    pageContent: pageContentReducer,
  },
})