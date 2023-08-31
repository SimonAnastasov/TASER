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
            <div>
                <Link to="/">
                    <img src="/images/logo.png" alt="TASER LOGO" className="object-contain w-16 h-16 lg:w-20 lg:h-20 rounded-full hover:border-white"/>
                </Link>
            </div>
            <div className="flex gap-4">
                {account?.loggedIn ? (
                    <>
                        <Link to="/history" className="--button button--primary-inverted">History</Link>
                        <button className="--button button--dark-inverted" onClick={handleLogOut}>Log Out</button>
                    </>
                ) : (
                    <>
                        <Link to="/signup" className="--button button--primary-inverted">Sign Up</Link>
                        <Link to="/login" className="--button button--dark-inverted">Log In</Link>
                    </>
                )}
            </div>
        </header>
    )
}

export default Header