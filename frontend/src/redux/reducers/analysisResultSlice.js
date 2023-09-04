import { createSlice } from '@reduxjs/toolkit'

export const analysisResultSlice = createSlice({
    name: 'analysis-result',
    initialState: {
        result: {},
        review: {},
    },
    reducers: {
        setAnalysisResult: (state, action) => {
            state.result = action.payload
        },
        setAnalysisReview: (state, action) => {
            state.review = action.payload
        }
    }
})

export const { setAnalysisResult, setAnalysisReview } = analysisResultSlice.actions;

export default analysisResultSlice.reducer;
