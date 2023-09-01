import React from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import axios from 'axios'
import { serverApiUrl } from '../../utils/envVariables'

import { setMessageLoginPage, setMessageRegisterPage } from '../../redux/reducers/accountSlice';

const SignUp = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const account = useSelector(state => state?.account);
    
    function handleSignUp() {
        const username = document.getElementById('usernameSignUp').value.toLowerCase();
        const password = document.getElementById('passwordSignUp').value;

        if (username && password && username.length > 0 && password.length > 0) {
            // Send request to server

            axios.post(`${serverApiUrl}/register`, {
                username, 
                password,
            })
                .then(response => {
                    const data = response?.data;
                    if (!data?.error) {
                        // Success -> Remove any success/error message from Sign Up Page
                        dispatch(setMessageRegisterPage({
                            message: "",
                            isError: false,
                        }));
                        
                        // Set success message on Login Page and redirect to Login Page
                        dispatch(setMessageLoginPage({
                            message: "You have successfully signed up! You can log in now.",
                            isError: false,
                        }));
                        navigate("/login");
                    }
                    else {
                        // Error -> Set error message on Sign Up Page
                        dispatch(setMessageRegisterPage({
                            message: data?.message,
                            isError: true,
                        }));
                    }
                })
                .catch(error => {
                    dispatch(setMessageRegisterPage({
                        message: "Unknown error. Please try again later.",
                        isError: true,
                    }));
                    console.log(error);
                });
        }
    }

    return (
        <div className="px-6 lg:px-0">
            <h2 className="mb-8 heading--3 text-center">Sign Up</h2>

            {account?.messageRegisterPage && (
                <p className={`heading--6 text-center ${account.messageRegisterPage.isError ? 'text-red-700' : 'text-green-700'}`}>
                    {account.messageRegisterPage.message}
                </p>
            )}

            <div className="mt-4 max-w-[400px] mx-auto flex flex-col justify-center items-center gap-4">
                <input id="usernameSignUp" className="w-full heading--6 py-3 px-8 rounded-xl bg-transparent border-2 border-black/20" type="text" placeholder="Username" onKeyDown={handleKeyDown}/>
                <input id="passwordSignUp" className="w-full heading--6 py-3 px-8 rounded-xl bg-transparent border-2 border-black/20" type="password" placeholder="Password" onKeyDown={handleKeyDown}/>

                <button className="lg:w-full --button button--success-inverted mt-6" onClick={handleSignUp}>Sign Up</button>
            </div>
        </div>
    )

    function handleKeyDown(e) {
        if (e.key === "Enter") {
            handleSignUp();
        }
    }
}

export default SignUp