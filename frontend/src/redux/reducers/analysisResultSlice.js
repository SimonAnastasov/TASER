import { createSlice } from '@reduxjs/toolkit'

export const analysisResultSlice = createSlice({
    name: 'analysis-result',
    initialState: {
        result: {},
        review: {},
        improvementInfo: {},
        employeeInfo: {}
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
        setAnalysisEmployeeInfo: (state, action) => {
            state.employeeInfo = action.payload
        }
    }
})

export const { setAnalysisResult, setAnalysisReview, setAnalysisImprovementInfo, setAnalysisEmployeeInfo } = analysisResultSlice.actions;

export default analysisResultSlice.reducer;
