/**
 * @jest-environment jsdom
 */

import store from '../redux/store'
import {render, screen} from "@testing-library/react";
import {Provider} from "react-redux";
import AudioProcessingPage from "../components/audioProcessing/AudioProcessingPage";
import React from "react";
import { BrowserRouter as Router } from 'react-router-dom';
import '@testing-library/jest-dom/extend-expect';
import { setAudioProcessingStatus } from '../redux/reducers/audioProcessingSlice';
import { useSelector } from 'react-redux'

describe ('Audio Processing page', () => {
    const testStore = store
    const produceProcessingComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <AudioProcessingPage/>
        </Router>
    </Provider>
    );

    test('Audio Processing state 0 test', () => {
        testStore.dispatch(setAudioProcessingStatus(0))
        produceProcessingComponent();
        const status = screen.getByText(/The server is receiving the Audio File/i);
        expect(status).toBeInTheDocument();
    });

    test('Audio Processing state 1 test', () => {
        testStore.dispatch(setAudioProcessingStatus(1))
        produceProcessingComponent();
        const status = screen.getByText(/The server is processing the Audio File/i);
        expect(status).toBeInTheDocument();
    });

    test('Audio Processing state 2 test', () => {
        testStore.dispatch(setAudioProcessingStatus(2))
        produceProcessingComponent();
        const status = screen.getByText(/The server has finished processing the Audio File/i);
        expect(status).toBeInTheDocument();
    });

    test('Audio Processing state 3 test', () => {
        testStore.dispatch(setAudioProcessingStatus(3))
        produceProcessingComponent();
        const status = screen.getByText(/The server has encountered an error(<br>)?/i);
        expect(status).toBeInTheDocument();
    });

})