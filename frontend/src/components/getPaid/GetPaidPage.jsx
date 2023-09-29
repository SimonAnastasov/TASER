import React from 'react'
import InfoButton from '../utils/InfoButton'
import { INFO_GET_PAID_PAGE } from '../../utils/infoTexts'

const GetPaidPage = () => {
    return (
        <div className="px-6 lg:px-0">
            <p className="heading--3 text-center mb-8">Get Paid - Improve Analyses</p>

            <div className="mb-16 lg:mb-20 w-fit mx-auto flex items-center gap-4">
                <div className="hidden">
                    <InfoButton infoText={INFO_GET_PAID_PAGE}/>
                </div>
                <button className="--button button--success">Request Analysis For Improving</button>
                <InfoButton infoText={INFO_GET_PAID_PAGE}/>
            </div>

            <hr className="container px-6 lg:px-0 mx-auto bg-primary/20 h-0.5"/>

            <div className="mt-16 lg:mt-20">

            </div>
        </div>
    )
}

export default GetPaidPage