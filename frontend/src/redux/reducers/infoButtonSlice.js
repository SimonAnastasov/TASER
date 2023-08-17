import { createSlice } from '@reduxjs/toolkit'

export const infoButtonSlice = createSlice({
    name: 'info-button',
    initialState: {
        text: "",
    },
    reducers: {
        setInfoText: (state, action) => {
            state.text = action.payload
        }
    }
})

export const { setInfoText } = infoButtonSlice.actions;

export default infoButtonSlice.reducer;
