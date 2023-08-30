import React from 'react'

const LogIn = () => {
    return (
        <div className="px-6 lg:px-0">
        <h2 className="heading--3 text-center">Log In</h2>

        <div className="mt-12 max-w-[400px] mx-auto flex flex-col justify-center items-center gap-4">
          <input className="w-full heading--6 py-3 px-8 rounded-xl bg-transparent border-2 border-black/20" type="text" placeholder="Username"/>
          <input className="w-full heading--6 py-3 px-8 rounded-xl bg-transparent border-2 border-black/20" type="password" placeholder="Password"/>

          <button className="lg:w-full --button button--success-inverted mt-6">Log In</button>
        </div>
    </div>
    )
}

export default LogIn