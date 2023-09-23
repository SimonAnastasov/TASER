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

describe ('Login page', () => {
        const testStore = store
    // testStore.dispatch(setAccount({username: "test"}))
    // testStore.dispatch(setLoggedIn(true))
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

    
})
