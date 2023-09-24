/**
 * @jest-environment jsdom
 */

import store from '../redux/store'
import {render, screen} from "@testing-library/react";
import {Provider} from "react-redux";
import React from "react";
import { BrowserRouter as Router } from 'react-router-dom';
import '@testing-library/jest-dom/extend-expect';
import { setAccount, setLoggedIn } from '../redux/reducers/accountSlice';
import { useSelector } from 'react-redux'
import HistoryPage from '../components/history/HistoryPage';

describe ('History page', () => {
    const testStore = store
    testStore.dispatch(setAccount({username: "test"}))
    testStore.dispatch(setLoggedIn(true))
    const produceHistoryComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <HistoryPage/>
        </Router>
    </Provider>
    );

    test('History component renders', () => {
        produceHistoryComponent();
        const history = screen.getByText(/History - Your Past Analyses/i);
        expect(history).toBeInTheDocument();
    }); 

    

    
})