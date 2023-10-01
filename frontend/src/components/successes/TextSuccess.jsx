import React from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { setTextSuccess } from '../../redux/reducers/successesSlice';

const TextSuccess = () => {
    const dispatch = useDispatch();
    
    const textSuccess = useSelector(state => state?.successes?.textSuccess);

    const isTextSuccess = textSuccess && typeof textSuccess === "string" && textSuccess.length > 0;

    return (
        <div className={`fixed inset-0 bg-green-700/20 rounded-xl flex flex-col justify-center items-center transition-all duration-300 ${isTextSuccess ? 'scale-100' : 'scale-0'}`}>
            <div className={`px-4 lg:px-8 py-4 lg:py-8 max-w-5xl mx-auto w-full bg-white rounded-xl flex flex-col justify-center items-center transition-all duration-300 delay-150 overflow-y-auto ${isTextSuccess ? 'scale-100' : 'scale-0'}`}>
                <p className="heading--2 text-green-700">Success!</p>

                <div className="my-12 heading--5 text-green-700 text-center px-4 lg:px-8 py-2 lg:py-3">
                    {textSuccess}
                </div>
                
                <button type="button" className="--button button--success-inverted" onClick={() => dispatch(setTextSuccess(""))}>Close</button>
            </div>
        </div>
    )
}

export default TextSuccess