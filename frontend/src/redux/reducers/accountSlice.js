import { createSlice } from '@reduxjs/toolkit'

export const accountSlice = createSlice({
    name: 'account',
    initialState: {
        account: {username: ""},
        loggedIn: false,
        messageRegisterPage: {message: "", isError: false},
        messageLoginPage: {message: "", isError: false},
    },
    reducers: {
        setAccount: (state, action) => {
            state.account = action.payload
        },
        setLoggedIn: (state, action) => {
            state.loggedIn = action.payload
        },
        setMessageRegisterPage: (state, action) => {
            state.messageRegisterPage = action.payload
        },
        setMessageLoginPage: (state, action) => {
            state.messageLoginPage = action.payload
        },
    }
})

export const { setAccount, setLoggedIn, setMessageRegisterPage, setMessageLoginPage } = accountSlice.actions;

export default accountSlice.reducer;
