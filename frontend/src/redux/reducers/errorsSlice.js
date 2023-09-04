import { createSlice } from '@reduxjs/toolkit'

export const errorsSlice = createSlice({
    name: 'errors',
    initialState: {
        textError: "",
    },
    reducers: {
        setTextError: (state, action) => {
            state.textError = action.payload
        }
    }
})

export const { setTextError } = errorsSlice.actions;

export default errorsSlice.reducer;
