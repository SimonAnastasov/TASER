import { createSlice } from '@reduxjs/toolkit'

export const shownAnalysisSlice = createSlice({
    name: 'shown-analysis',
    initialState: {
        // 0 for global analysis
        // 1 for segment by segment analysis
        shownAnalysis: 0,
    },
    reducers: {
        setShownAnalysis: (state, action) => {
            state.shownAnalysis = action.payload
        }
    }
})

export const { setShownAnalysis } = shownAnalysisSlice.actions;

export default shownAnalysisSlice.reducer;
