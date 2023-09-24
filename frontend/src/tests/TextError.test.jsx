/**
 * @jest-environment jsdom
 */

import store from '../redux/store'
import {render, screen} from "@testing-library/react";
import {Provider} from "react-redux";
import TextError from "../components/errors/TextError";
import React from "react";
import { BrowserRouter as Router } from 'react-router-dom';
import '@testing-library/jest-dom/extend-expect';
import { setTextError } from '../redux/reducers/errorsSlice';
import { useSelector } from 'react-redux'

describe ('Text Error Page', () => {
    const testStore = store
    const produceErrorComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <TextError/>
        </Router>
    </Provider>
    );

    test('Test error component', () => {
        testStore.dispatch(setTextError("Test Error"))
        produceErrorComponent();
        const error = screen.getByText(/Test Error/i);
        expect(error).toBeInTheDocument();
    }); 

    
})