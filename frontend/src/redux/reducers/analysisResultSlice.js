import { createSlice } from '@reduxjs/toolkit'

export const analysisResultSlice = createSlice({
    name: 'analysis-result',
    initialState: {
        result: {},
        review: {},
        improvementInfo: {},
    },
    reducers: {
        setAnalysisResult: (state, action) => {
            state.result = action.payload
        },
        setAnalysisReview: (state, action) => {
            state.review = action.payload
        },
        setAnalysisImprovementInfo: (state, action) => {
            state.improvementInfo = action.payload
        },
    }
})

export const { setAnalysisResult, setAnalysisReview, setAnalysisImprovementInfo } = analysisResultSlice.actions;

export default analysisResultSlice.reducer;
