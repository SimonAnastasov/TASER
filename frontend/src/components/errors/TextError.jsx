import React from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { setTextError } from '../../redux/reducers/errorsSlice';

const TextError = () => {
    const dispatch = useDispatch();
    
    const textError = useSelector(state => state?.errors?.textError);

    const isTextError = textError && typeof textError === "string" && textError.length > 0;

    return (
        <div className={`fixed inset-0 bg-red-700/20 rounded-xl flex flex-col justify-center items-center transition-all duration-300 ${isTextError ? 'scale-100' : 'scale-0'}`}>
            <div className={`px-4 lg:px-8 py-4 lg:py-8 max-w-5xl mx-auto w-full bg-white rounded-xl flex flex-col justify-center items-center transition-all duration-300 delay-150 overflow-y-auto ${isTextError ? 'scale-100' : 'scale-0'}`}>
                <p className="heading--2 text-red-700">Error!</p>

                <div className="my-12 heading--5 text-red-700 text-center px-4 lg:px-8 py-2 lg:py-3">
                    {textError}
                </div>
                
                <button type="button" className="--button button--error-inverted" onClick={() => dispatch(setTextError(""))}>Close</button>
            </div>
        </div>
    )
}

export default TextError