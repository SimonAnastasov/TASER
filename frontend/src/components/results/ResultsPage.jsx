import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'

import GlobalAnalysisResults from './GlobalAnalysisResults'
import SegmentBySegmentAnalysisResults from './SegmentBySegmentAnalysisResults'
import ToggleShownAnalysis from './ToggleShownAnalysis'

import InfoButton from '../utils/InfoButton'
import { INFO_IMPROVE_THIS_ANALYSIS, INFO_RESULTS_PAGE } from '../../utils/infoTexts'
import { useNavigate } from 'react-router-dom'
import { setAnalysisReview } from '../../redux/reducers/analysisResultSlice'
import { getCookie } from '../../utils/functions/cookies'
import axios from 'axios'
import { serverApiUrl } from '../../utils/envVariables'
import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice'
import { setTextError } from '../../redux/reducers/errorsSlice'

const ResultsPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const [isActiveLeaveReview, setIsActiveLeaveReview] = useState(false);
    
    const analysis = useSelector(state => state?.analysisResult?.result);
    const analysisResult = analysis?.id ? JSON.parse(analysis?.text) : null;

    const analysisReview = useSelector(state => state?.analysisResult?.review);

    const shownAnalysis = useSelector(state => state?.shownAnalysis?.shownAnalysis);

    const account = useSelector(state => state?.account)

    // Get approximation on price for improving analysis
    const dataSize = new TextEncoder().encode(JSON.stringify(analysisResult)).length;
    const priceCoefficient = 0.0001;
    const priceForImproving = (dataSize * priceCoefficient).toFixed(2);

    return (
        <div className="px-6 lg:px-0">
            {analysisResult && Object.keys(analysisResult).length > 0 ? (
                <>
                    <div className="mb-6">
                        <p className="--small-text text-center">Filename:</p>
                        <p className="heading--6 text-center">{analysis.filename}</p>
                    </div>

                    <div className="mb-16 w-fit mx-auto flex items-center gap-4">
                        <div className="hidden">
                            <InfoButton infoText={INFO_IMPROVE_THIS_ANALYSIS.replace(/\[\[\[PRICE\]\]\]/g, priceForImproving)}/>
                        </div>
                        <button className="--button button--success">Improve This Analysis</button>
                        <InfoButton infoText={INFO_IMPROVE_THIS_ANALYSIS.replace(/\[\[\[PRICE\]\]\]/g, priceForImproving)}/>
                    </div>

                    <div className="mb-8 lg:mb-16 w-fit mx-auto text-sm lg:text-lg">
                        <ToggleShownAnalysis/>
                    </div>

                    <div className="flex justify-center items-center">
                        <InfoButton infoText={INFO_RESULTS_PAGE}/>
                    </div>

                    {
                        shownAnalysis === 0 ? (
                            <GlobalAnalysisResults/>
                        ) : (
                            <SegmentBySegmentAnalysisResults/>
                        )
                    }

                    {account.loggedIn ? (
                        <>
                            {!isActiveLeaveReview ? (
                                <div className="mt-24">
                                    {analysisReview && Object.keys(analysisReview).length > 0 ? (
                                        <div className="flex flex-col gap-6 justify-center items-center max-w-3xl mx-auto">
                                            <p className="heading--5 text-center">Your review ({analysisReview.reviewRating} ⭐️):</p>
                                            <p className="heading--6 text-justify">{analysisReview.reviewText}</p>
                                            <div className="flex flex-col lg:flex-row gap-4 lg:gap-8 justify-center items-center">
                                                <button className="--button button--primary" onClick={showReviewEditFields}>Update review 📝</button>
                                                <button className="--button button--error" onClick={deleteReview}>Delete review 🗑️</button>
                                            </div>
                                        </div>
                                    ) : (
                                        <div className="flex flex-col gap-4 items-center">
                                            <p className="heading--6 text-center">You have not left a review for this analysis.</p>
                                            <button className="--button button--primary" onClick={showReviewEditFields}>Leave a review 📝</button>
                                        </div>
                                    )}
                                </div>
                            ) : (
                                <div className="mt-24 flex flex-col gap-4">
                                    <div className="flex flex-row justify-center items-center gap-4">
                                        <p className="heading--5 text-center">Leave your review:</p>
                                        <div className="flex flex-row gap-4 justify-center items-center">
                                            <input className="rounded-xl heading--5 py-3 px-2 w-24 text-center bg-white/80" id="reviewRating" name="reviewRating" placeholder="0" type="text" onKeyDown={handleKeyDownReviewRating} onKeyUp={handleKeyUpReviewRating}/>
                                            <p className="heading--5 text-center">⭐️</p>
                                        </div>
                                    </div>
                                    <div>
                                        <textarea className="mt-2 rounded-xl heading--6 py-4 px-4 w-full block max-w-5xl mx-auto h-32 bg-white/80" id="reviewText" name="reviewText" placeholder="Your thoughts..." type="text"/>
                                    </div>
                                    <div className="mt-6 flex flex-col lg:flex-row gap-4 lg:gap-8 justify-center items-center">
                                        <button className="--button button--primary" onClick={handleSaveReview}>Save review 📝</button>
                                        <button className="--button button--transparent" onClick={handleCancelLeaveReview}>Cancel ❌</button>
                                    </div>
                                </div>
                            )}
                        </>
                    ) : (
                        <div className="mt-24 flex flex-col gap-4">
                            <p className="heading--5 text-center">Log in to leave a review.</p>
                        </div>
                    )}

                    <div className="py-20"></div>
                </>
            ) : (
                <>
                    <p className="heading--3 text-center">No analysis to be shown.</p>
                    <div className="mt-8 flex flex-col lg:flex-row gap-4 lg:gap-8 justify-center items-center">
                        <button className="--button button--primary" onClick={handleGoBackHome}>← Go Back Home</button>
                        <button className="--button button--primary" onClick={handleViewPastAnalyses}>View Past Analyses →</button>
                    </div>
                </>
            )}
        </div>
    )

    function handleKeyDownReviewRating(e) {
        if (!/^\d*\.?\d*$/.test(e.key) && e.key !== 'Backspace' && e.key !== 'Enter') {
            e.preventDefault();
        }
    }

    function handleKeyUpReviewRating(e) {
        const reviewRatingInput = e.target;

        const reviewRating = parseFloat(reviewRatingInput.value);

        if (reviewRating < 0) {
            reviewRatingInput.value = '0';
        } else if (reviewRating > 5) {
            reviewRatingInput.value = '5';
        }
    }

    function showReviewEditFields(e) {
        setIsActiveLeaveReview(true);

        setTimeout(() => {
            if (document.getElementById("reviewRating") && document.getElementById("reviewText")) {
                document.getElementById("reviewRating").value = analysisReview?.reviewRating || 0;
                document.getElementById("reviewText").value = analysisReview?.reviewText || '';
            }
        }, 100)
    }

    function handleCancelLeaveReview(e) {
        setIsActiveLeaveReview(false);
    }

    function handleGoBackHome(e) {
        navigate("/");
    }

    function handleViewPastAnalyses(e) {
        navigate("/history");
    }

    function handleSaveReview(e) {
        if (!document.getElementById("reviewRating")) return ;
        if (!document.getElementById("reviewText")) return ;

        const reviewRating = parseFloat(document.getElementById("reviewRating").value);
        const reviewText = document.getElementById("reviewText").value;

        if (reviewRating < 0 || reviewRating > 5) {
            dispatch(setTextError("Please enter a valid review rating (0 - 5)."));
            return;
        }

        if (reviewText.length < 1) {
            dispatch(setTextError("Please enter a valid review text."));
            return;
        }

        const review = {
            reviewRating: reviewRating,
            reviewText: reviewText
        }

        let headers = {}
            
        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        if (analysisReview && Object.keys(analysisReview).length > 0) {
            // PUT
            axios.put(`${serverApiUrl}/review/${analysisReview.id}`, review, {
                headers: headers
            })
                .then(response => {
                    const data = response?.data;
                    if (!data?.error) {
                        dispatch(setAnalysisReview(data.review));
                        setIsActiveLeaveReview(false);
                    }
                    else {
                        if (data.notLoggedIn) {
                            if (account.loggedIn) {
                                dispatch(setLoggedIn(false));
                                dispatch(setAccount({ username: "" }));

                                setIsActiveLeaveReview(false);   
                                dispatch(setTextError("Please log in to leave or update a review."));                             
                            }
                        }
                        else {
                            setIsActiveLeaveReview(false);
                            dispatch(setTextError(data.message));
                        }
                    }
                })
                .catch(error => {
                    setIsActiveLeaveReview(false);
                    dispatch(setTextError("Unknown error. Please try again later."));
                })
        }
        else {
            // POST
            axios.post(`${serverApiUrl}/transcription/${analysis.id}/review`, review, {
                headers: headers
            })
                .then(response => {
                    const data = response?.data;
                    if (!data?.error) {
                        dispatch(setAnalysisReview(data.review));
                        setIsActiveLeaveReview(false);
                    }
                    else {
                        if (data.notLoggedIn) {
                            if (account.loggedIn) {
                                dispatch(setLoggedIn(false));
                                dispatch(setAccount({ username: "" }));

                                setIsActiveLeaveReview(false);   
                                dispatch(setTextError("Please log in to leave or update a review."));                             
                            }
                        }
                        else {
                            setIsActiveLeaveReview(false);
                            dispatch(setTextError(data.message));
                        }
                    }
                })
                .catch(error => {
                    setIsActiveLeaveReview(false);
                    dispatch(setTextError("Unknown error. Please try again later."));
                })
        }
    }

    function deleteReview(e) {
        // DELETE
        let headers = {}

        let bearerToken = getCookie("bearerToken");
        if (typeof bearerToken === "string" && bearerToken.length > 0) {
            headers["Authorization"] = `${bearerToken}`;
        }

        if (analysisReview && Object.keys(analysisReview).length > 0) {
            axios.delete(`${serverApiUrl}/review/${analysisReview.id}`, {
                headers: headers
            })
                .then(response => {
                    const data = response?.data;
                    if (!data?.error) {
                        dispatch(setAnalysisReview({}));
                    }
                    else {
                        if (data.notLoggedIn) {
                            if (account.loggedIn) {
                                dispatch(setLoggedIn(false));
                                dispatch(setAccount({ username: "" }));
                            }
                        }
                        else {
                            dispatch(setTextError(data.message));
                        }
                    }
                })
                .catch(error => {
                    dispatch(setTextError("Unknown error. Please try again later."));
                })
        }
    }
}

export default ResultsPage