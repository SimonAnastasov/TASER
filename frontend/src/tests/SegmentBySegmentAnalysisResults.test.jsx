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
import { TEST_ANALYSIS_RESULT_RESPONSE } from './utils/testingData'
import SegmentBySegmentAnalysisResults from '../components/results/SegmentBySegmentAnalysisResults';

describe ('Global Analysis Results', () => {
    const testStore = store
    const produceSegmentAnalysisResultsComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <SegmentBySegmentAnalysisResults/>
        </Router>
    </Provider>
    );

    test('segment by segment analysis results component shows analysis', () => {
        const analysis = TEST_ANALYSIS_RESULT_RESPONSE
        const analysisResult = analysis?.transcription?.id ? JSON.parse(analysis?.transcription?.text) : null;
        testStore.dispatch(setAnalysisResult(analysis.transcription))
        produceSegmentAnalysisResultsComponent();
        let expectedSentiment = analysisResult.segments[0].sentiment[0].toUpperCase() + analysisResult.segments[0].sentiment.slice(1)
        let expectedSentimentText = "Sentiment: " + expectedSentiment
        const sentiment = screen.getAllByText(expectedSentimentText);
        expect(sentiment).toHaveLength(4);
    }); 

    test('segment by segment analysis results component shows speaker', () => {
        const analysis = TEST_ANALYSIS_RESULT_RESPONSE
        const analysisResult = analysis?.transcription?.id ? JSON.parse(analysis?.transcription?.text) : null;
        testStore.dispatch(setAnalysisResult(analysis.transcription))
        produceSegmentAnalysisResultsComponent();
        let expectedSpeaker = analysisResult.segments[0].speaker
        const speaker = screen.getAllByText(expectedSpeaker);
        expect(speaker).toHaveLength(2);
    });

    test('segment by segment analysis results component shows segment number', () => {
        const analysis = TEST_ANALYSIS_RESULT_RESPONSE
        const analysisResult = analysis?.transcription?.id ? JSON.parse(analysis?.transcription?.text) : null;
        testStore.dispatch(setAnalysisResult(analysis.transcription))
        produceSegmentAnalysisResultsComponent();
        let expectedSegmentNumber = "Segment 1/" + analysisResult.segments.length
        const segmentNumber = screen.getByText(expectedSegmentNumber);
        expect(segmentNumber).toBeInTheDocument();
    });


})