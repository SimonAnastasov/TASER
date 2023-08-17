import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { setInfoText } from '../../redux/reducers/infoButtonSlice';

const InfoButton = ({ infoText }) => {
    const [infoBoxIsActive, setInfoBoxIsActive] = React.useState(false);

    const currentInfoText = useSelector(state => state.infoButton.text)
    const dispatch = useDispatch();

    function infoButtonMouseOver() {
        setInfoBoxIsActive(true);

        dispatch(setInfoText(infoText));
    }

    function infoButtonMouseLeave() {
        setInfoBoxIsActive(false);
    }

    return (
        <div className={`relative z-10`} onMouseOver={infoButtonMouseOver} onMouseLeave={infoButtonMouseLeave}>
            <img src="/images/icon-info.png" className="w-6 h-6" alt="icon info"/>
            <div className={`w-[500px] ${infoBoxIsActive ? 'flex' : 'hidden'} flex-col items-center gap-8 rounded-xl bg-black text-white shadow-md text-lg py-6 px-8 fixed lg:absolute max-w-[90vw] lg:max-w-none left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 transition-all duration-300`}>
                <span className="block" dangerouslySetInnerHTML={{__html: currentInfoText }}></span>
                <button type="button" className="--button button--dark" onClick={infoButtonMouseLeave}>Close</button>
            </div>
        </div>
    )
}

export default InfoButton