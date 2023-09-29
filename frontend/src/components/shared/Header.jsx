import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import { clearCookie } from '../../utils/functions/cookies';
import { setAccount, setLoggedIn } from '../../redux/reducers/accountSlice';

const Header = () => {
    const dispatch = useDispatch();
    
    const account = useSelector(state => state?.account)

    function handleLogOut() {
        clearCookie("bearerToken");
        clearCookie("username");
        dispatch(setLoggedIn(false));
        dispatch(setAccount({username: ""}));
    }

    return (
        <header className="absolute inset-0 bottom-auto bg-primary text-white py-4 px-4 lg:px-16 flex justify-between items-center">
            <div className="flex-shrink-0">
                <Link to="/">
                    <img src="/images/logo.png" alt="TASER LOGO" className="object-contain w-16 h-16 lg:w-20 lg:h-20 rounded-full hover:border-white"/>
                </Link>
            </div>
            <div className="flex gap-1 lg:gap-4">
                {account?.loggedIn ? (
                    <>
                        <Link to="/get-paid" className="break-all text-center flex justify-center items-center --button-small button--primary-inverted">Get Paid</Link>
                        <Link to="/history" className="break-all text-center flex justify-center items-center --button-small button--primary-inverted">History</Link>
                        <button className="break-all text-center flex justify-center items-center --button-small button--dark-inverted" onClick={handleLogOut}>Log Out</button>
                    </>
                ) : (
                    <>
                        <Link to="/signup" className="break-all text-center flex justify-center items-center --button-small button--primary-inverted">Sign Up</Link>
                        <Link to="/login" className="break-all text-center flex justify-center items-center --button-small button--dark-inverted">Log In</Link>
                    </>
                )}
            </div>
        </header>
    )
}

export default Header