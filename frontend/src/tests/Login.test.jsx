/**
 * @jest-environment jsdom
 */

import store from '../redux/store'
import {render, screen} from "@testing-library/react";
import {Provider} from "react-redux";
import LogIn from "../components/accounts/LogIn";
import React from "react";
import { BrowserRouter as Router } from 'react-router-dom';
import '@testing-library/jest-dom/extend-expect';
import { setAccount, setLoggedIn } from '../redux/reducers/accountSlice';
import { useSelector } from 'react-redux'
import { act } from 'react-dom/test-utils';


describe ('Login page', () => {
    const testStore = store
    const produceLoginComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <LogIn/>
        </Router>
    </Provider>
    );

    test('Login component renders', () => {
        produceLoginComponent();
        const log_in = screen.getAllByText(/Log In/i);
        expect(log_in).toHaveLength(2);
    }); 

    test('Test logging in', async () => {
        // find username and password fields and input values
        produceLoginComponent();
        const username = screen.getByPlaceholderText("Username");
        const password = screen.getByPlaceholderText("Password");
        const usernameValue = "testuser"
        const passwordValue = "testuser"
        // input values
        username.value = usernameValue
        password.value = passwordValue
        // find submit button and click
        await act(async () => {
            const submitButton = screen.getByRole('submit-button');
            submitButton.click()
        })
        // check if logged in
        const state = testStore.getState();
        expect(state.account?.loggedIn).toBe(true);
    });

    test('Test logging in with incorrect password', async () => {
        //reset store
        testStore.dispatch(setAccount({username: ""}))
        testStore.dispatch(setLoggedIn(false))
        // find username and password fields and input values
        produceLoginComponent();
        const username = screen.getByPlaceholderText("Username");
        const password = screen.getByPlaceholderText("Password");
        const usernameValue = "testuser"
        const passwordValue = "wrongpassword"
        // input values
        username.value = usernameValue
        password.value = passwordValue
        // find submit button and click
        await act(async () => {
            const submitButton = screen.getByRole('submit-button');
            submitButton.click()
        })
        // check if logged in
        const state = testStore.getState();
        expect(state.account.loggedIn).toBe(false);
        });

    test('test if bearer token is set', async () => {
        produceLoginComponent();
        const username = screen.getByPlaceholderText("Username");
        const password = screen.getByPlaceholderText("Password");
        const usernameValue = "testuser"
        const passwordValue = "testuser"
        // input values
        username.value = usernameValue
        password.value = passwordValue
        // find submit button and click
        await act(async () => {
            const submitButton = screen.getByRole('submit-button');
            submitButton.click()
        })
        // check if bearer token is set
        const bearerToken = document.cookie 
        expect(bearerToken).toContain("bearerToken")
    });

    
})
