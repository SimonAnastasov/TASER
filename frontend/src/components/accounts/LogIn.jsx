import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom';

import axios from 'axios'
import { serverApiUrl } from '../../utils/envVariables'

import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice';

import { setCookie } from '../../utils/functions';

const LogIn = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    
    const account = useSelector(state => state?.account);

    function handleLogIn() {
        const username = document.getElementById('usernameLogIn').value;
        const password = document.getElementById('passwordLogIn').value;

        if (username && password && username.length > 0 && password.length > 0) {
            // Send request to server

            axios.post(`${serverApiUrl}/login`, {
                username, 
                password,
            })
                .then(response => {
                    if (response.status === 200) {
                        setCookie("bearerToken", response.data, 10);
                        
                        dispatch(setLoggedIn(true));
                        dispatch(setAccount({username}));
                        navigate("/");
                    }
                })
                .catch(error => {
                    alert("Error");
                    console.log(error);
                });
        }
    }

    return (
        <div className="px-6 lg:px-0">
            <h2 className="mb-8 heading--3 text-center">Log In</h2>

            {account?.messageLoginPage && (
                <p className={`heading--6 text-center ${account.messageLoginPage.isError ? 'text-red-700' : 'text-green-700'}`}>
                    {account.messageLoginPage.message}
                </p>
            )}

            <div className="mt-4 max-w-[400px] mx-auto flex flex-col justify-center items-center gap-4">
                <input id="usernameLogIn" className="w-full heading--6 py-3 px-8 rounded-xl bg-transparent border-2 border-black/20" type="text" placeholder="Username"/>
                <input id="passwordLogIn" className="w-full heading--6 py-3 px-8 rounded-xl bg-transparent border-2 border-black/20" type="password" placeholder="Password"/>

                <button className="lg:w-full --button button--success-inverted mt-6" onClick={handleLogIn}>Log In</button>
            </div>
        </div>
    )
}

export default LogIn