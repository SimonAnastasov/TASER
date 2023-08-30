import React from 'react'
import { Link } from 'react-router-dom'

const Header = () => {
    return (
        <header className="absolute inset-0 bottom-auto bg-primary text-white py-4 px-4 lg:px-16 flex justify-between items-center">
            <div>
                <Link to="/">
                    <img src="/images/logo.png" alt="TASER LOGO" className="object-contain w-16 h-16 lg:w-20 lg:h-20 rounded-full hover:border-white"/>
                </Link>
            </div>
            <div className="flex gap-4">
                <Link to="/signup" className="--button button--primary-inverted">Sign Up</Link>
                <Link to="/login" className="--button button--dark-inverted">Log In</Link>
            </div>
        </header>
    )
}

export default Header