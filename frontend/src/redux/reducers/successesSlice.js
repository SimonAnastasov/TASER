import { createSlice } from '@reduxjs/toolkit'

export const successesSlice = createSlice({
    name: 'successes',
    initialState: {
        textSuccess: "",
    },
    reducers: {
        setTextSuccess: (state, action) => {
            state.textSuccess = action.payload
        }
    }
})

export const { setTextSuccess } = successesSlice.actions;

export default successesSlice.reducer;
