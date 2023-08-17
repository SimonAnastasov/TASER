import { createSlice } from '@reduxjs/toolkit'

export const analysisResultSlice = createSlice({
    name: 'analysis-result',
    initialState: {
        result: {},
    },
    reducers: {
        setAnalysisResult: (state, action) => {
            state.result = action.payload
        }
    }
})

export const { setAnalysisResult } = analysisResultSlice.actions;

export default analysisResultSlice.reducer;
