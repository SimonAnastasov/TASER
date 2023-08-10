import React from 'react'

const Header = () => {
  return (
    <header className="absolute inset-0 bottom-auto bg-primary text-white py-4 px-4 lg:px-16 flex justify-between items-center">
        <div>
          <a href="./">
            <img src="/logo.png" alt="TASER LOGO" className="object-contain w-20 h-20 rounded-full hover:border-white"/>
          </a>
        </div>
        <div className="flex gap-4">
          {/* Replace with <a> tags */}
          <button type="button" className="--button button--dark-inverted">Log In</button>
          <button type="button" className="--button button--primary-inverted">Sign Up</button>
        </div>
    </header>
  )
}

export default Header