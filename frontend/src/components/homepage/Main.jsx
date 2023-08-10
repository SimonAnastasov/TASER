import React from 'react'

const Homepage = () => {
  return (
    <div>
        <h1 className="heading--3 text-center">Talk Analysis & Sound Extraction Resource</h1>
        <p className="--small-text text-center mt-6">Free Version TASER-F</p>

        <div className="w-fit mx-auto mt-16 lg:mt-20 flex justify-center items-center p-4 border-2 border-primary rounded-xl">
            <div className="text-primary p-20 lg:px-40 flex flex-col gap-2 items-center">
                <img src="/images/icon-sound.png" className="w-12" alt="icon sound"/>
                <p className="heading--5 text-center">Drag & Drop or Click<br/>to Upload File</p>
            </div>
        </div>
    </div>
  )
}

export default Homepage