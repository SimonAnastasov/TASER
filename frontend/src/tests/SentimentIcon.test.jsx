/**
 * @jest-environment jsdom
 */

import store from '../redux/store'
import {render, screen} from "@testing-library/react";
import {Provider} from "react-redux";
import SentimentIcon from '../components/utils/SentimentIcon';
import React from "react";
import { BrowserRouter as Router } from 'react-router-dom';
import '@testing-library/jest-dom/extend-expect';


describe ('Sentiment Icon', () => {
    const testStore = store
    const produceSentimentIconComponent = (sentiment) =>
    render(
    <Provider store={testStore}>
        <Router>
         <SentimentIcon sentiment={sentiment} />
        </Router>
    </Provider>
    );

    test('Positive Sentiment Icon renders', () => {
        produceSentimentIconComponent("positive");
        const icon = screen.getByAltText(/icon green happy smiley/i);
        expect(icon).toBeInTheDocument();
    }); 

    test('Negative Sentiment Icon renders', () => {
        produceSentimentIconComponent("negative");
        const icon = screen.getByAltText(/icon red sad smiley/i);
        expect(icon).toBeInTheDocument();
    });

    
})