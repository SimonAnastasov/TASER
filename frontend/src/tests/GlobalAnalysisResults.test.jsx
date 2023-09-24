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
import { TEST_ANALYSIS_RESULT_RESPONSE } from '../utils/testingData'

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

    test('Test global analysis results component', () => {
        const analysisResult = TEST_ANALYSIS_RESULT_RESPONSE
        testStore.dispatch(setAnalysisResult(analysisResult))
        produceGlobalAnalysisResultsComponent();
        console.log(analysisResult)
        let expectedSentiment = analysisResult?.Global_sentiment[0].toUpperCase() + analysisResult?.Global_sentiment.slice(1)
        let expectedSentimentText = "Sentiment: " + expectedSentiment
        const sentiment = screen.getByText(expectedSentimentText);
        expect(sentiment).toBeInTheDocument();
    }); 

    
})
