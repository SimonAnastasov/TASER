import { createSlice } from "@reduxjs/toolkit";

export const pageContentSlice = createSlice({
    name: "page-content",
    initialState: {
        // 0 for homepage (audio uplaod screen)
        // 1 for audio processing page (while waiting response from server)
        // 2 for results page
        content: 0,
    },
    reducers: {
        setPageContent: (state, action) => {
            state.content = action.payload;
        },
    }
});

export const { setPageContent } = pageContentSlice.actions;

export default pageContentSlice.reducer;
