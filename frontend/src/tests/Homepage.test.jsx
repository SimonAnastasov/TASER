/**
 * @jest-environment jsdom
 */

import store from '../redux/store'
import {render, screen} from "@testing-library/react";
import {Provider} from "react-redux";
import Homepage from "../components/homepage/Homepage";
import React from "react";
import { BrowserRouter as Router } from 'react-router-dom';
import '@testing-library/jest-dom/extend-expect';
import { setAccount, setLoggedIn } from '../redux/reducers/accountSlice';
import { useSelector } from 'react-redux'



describe ('Home page', () => {
    const testStore = store
    testStore.dispatch(setAccount({username: "test"}))
    testStore.dispatch(setLoggedIn(true))
    testStore
    const produceHomepageComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <Homepage/>
        </Router>
    </Provider>
    );

    test('Homepage component renders', () => {
        produceHomepageComponent();
        const free_version = screen.getByText(/Free Version TASER-F/i);
        expect(free_version).toBeInTheDocument();
    });

    test('Welcome message renders', () => {
        produceHomepageComponent()
        let account = testStore.getState().account
        let username = account?.account?.username
        const welcome = screen.getByText('Welcome, ' + username + '!' )
        expect(welcome).toBeInTheDocument()
    });

})