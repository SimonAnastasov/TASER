import React from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { setShownAnalysis } from '../../redux/reducers/shownAnalysisSlice';

const ToggleShownAnalysis = () => {
    const dispatch = useDispatch();

    const shownAnalysis = useSelector(state => state?.shownAnalysis?.shownAnalysis);

    return (
        <div className="grid grid-cols-2 overflow-hidden border shadow-md">
            {/* Global Analysis */}
            <div className={`flex-1 relative border-r-[1px] border-light-hover py-3 px-6 lg:px-12 overflow-hidden group cursor-pointer flex justify-center items-center
                            ${shownAnalysis === 0 ? 'text-white' : 'text-black'}`}
                            onClick={() => dispatch(setShownAnalysis(0))}
            >
                <div className={`absolute inset-0 transition-all duration-300 group-hover:translate-x-0
                                ${shownAnalysis === 0 ? 'bg-primary translate-x-0' : 'bg-primary/10 translate-x-full'}`}
                >

                </div>

                <div className="absolute inset-0 flex justify-end items-center">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className={`w-5 h-5 transition-all duration-300 lg:group-hover:-translate-x-full rotate-[225deg] ${shownAnalysis === 0 ? 'lg:-translate-x-full' : 'translate-x-1/3'}`}>
                        <path fillRule="evenodd" d="M15.75 2.25H21a.75.75 0 01.75.75v5.25a.75.75 0 01-1.5 0V4.81L8.03 17.03a.75.75 0 01-1.06-1.06L19.19 3.75h-3.44a.75.75 0 010-1.5zm-10.5 4.5a1.5 1.5 0 00-1.5 1.5v10.5a1.5 1.5 0 001.5 1.5h10.5a1.5 1.5 0 001.5-1.5V10.5a.75.75 0 011.5 0v8.25a3 3 0 01-3 3H5.25a3 3 0 01-3-3V8.25a3 3 0 013-3h8.25a.75.75 0 010 1.5H5.25z" clipRule="evenodd" />
                    </svg>
                </div>
                
                <div className="relative text-center">
                    🌐&nbsp;
                    <div className="lg:hidden"></div> 
                    Global Analysis
                </div>
            </div>
            
            {/* Segment By Segment Analysis */}
            <div className={`flex-1 relative border-l-[1px] border-light-hover py-3 px-6 lg:px-12 overflow-hidden group cursor-pointer flex justify-center items-center
                            ${shownAnalysis === 1 ? 'text-white' : 'text-black'}`}
                            onClick={() => dispatch(setShownAnalysis(1))}
            >
                <div className={`absolute inset-0 transition-all duration-300 -translate-x-full group-hover:translate-x-0
                                ${shownAnalysis === 1 ? 'bg-black translate-x-0' : 'bg-black/10 -translate-x-full'}`}
                >

                </div>

                <div className="absolute inset-0 flex justify-start items-center">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className={`w-5 h-5 transition-all duration-300 lg:group-hover:translate-x-full rotate-45 ${shownAnalysis === 1 ? 'lg:translate-x-full' : '-translate-x-1/3'}`}>
                        <path fillRule="evenodd" d="M15.75 2.25H21a.75.75 0 01.75.75v5.25a.75.75 0 01-1.5 0V4.81L8.03 17.03a.75.75 0 01-1.06-1.06L19.19 3.75h-3.44a.75.75 0 010-1.5zm-10.5 4.5a1.5 1.5 0 00-1.5 1.5v10.5a1.5 1.5 0 001.5 1.5h10.5a1.5 1.5 0 001.5-1.5V10.5a.75.75 0 011.5 0v8.25a3 3 0 01-3 3H5.25a3 3 0 01-3-3V8.25a3 3 0 013-3h8.25a.75.75 0 010 1.5H5.25z" clipRule="evenodd" />
                    </svg>
                </div>
                
                <div className="relative text-center">
                    <div className="lg:hidden">📑</div> 
                    Segment By Segment Analysis 
                    <span className="hidden lg:inline"> 📑</span>
                </div>
            </div>
        </div>
    )
}

export default ToggleShownAnalysis