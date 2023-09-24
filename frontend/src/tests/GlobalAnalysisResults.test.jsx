/**
 * @jest-environment jsdom
 */

import store from '../redux/store'
import {render, screen} from "@testing-library/react";
import {Provider} from "react-redux";
import React from "react";
import { BrowserRouter as Router } from 'react-router-dom';
import '@testing-library/jest-dom/extend-expect';
import { setAnalysisResult, setAnalysisReview } from '../redux/reducers/analysisResultSlice';
import { useSelector } from 'react-redux'
import GlobalAnalysisResults from '../components/results/GlobalAnalysisResults';
import { TEST_ANALYSIS_RESULT_RESPONSE } from './utils/testingData'

describe ('Global Analysis Results', () => {
    const testStore = store
    const produceGlobalAnalysisResultsComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <GlobalAnalysisResults/>
        </Router>
    </Provider>
    );

    test('global analysis results component shows analysis', () => {
        const analysis = TEST_ANALYSIS_RESULT_RESPONSE
        const analysisResult = analysis?.transcription?.id ? JSON.parse(analysis?.transcription?.text) : null;
        testStore.dispatch(setAnalysisResult(analysis.transcription))
        produceGlobalAnalysisResultsComponent();
        let expectedSentiment = analysisResult?.Global_sentiment[0].toUpperCase() + analysisResult?.Global_sentiment.slice(1)
        let expectedSentimentText = "Sentiment: " + expectedSentiment
        const sentiment = screen.getByText(expectedSentimentText);
        expect(sentiment).toBeInTheDocument();
    }); 

    test('global analysis results component shows no analysis', () => {
        testStore.dispatch(setAnalysisResult({}))
        produceGlobalAnalysisResultsComponent();
        const sentiment = screen.queryByText(/Sentiment/i);
        expect(sentiment).not.toBeInTheDocument();
    });

    
})
