import { configureStore } from '@reduxjs/toolkit'
import infoButtonReducer from './reducers/infoButtonSlice'

export default configureStore({
  reducer: {
    infoButton: infoButtonReducer,
  },
})