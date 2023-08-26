import React from 'react'

const SentimentIcon = ({ sentiment }) => {
    return (
        <>
            {
                sentiment === "positive" ? (
                    <img src="/images/icon-green-smiley.png" className="w-8" alt="icon green happy smiley"/>
                ) : sentiment === "negative" ? (
                    <img src="/images/icon-red-smiley.png" className="w-8" alt="icon red sad smiley"/>
                ) : (<></>)
            }
        </>
    )
}

export default SentimentIcon