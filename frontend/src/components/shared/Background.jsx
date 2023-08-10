import React from 'react'

const Background = () => {
  return (
    <div className="z-[-1] fixed inset-0 opacity-20">
      <img src="/images/bg.jpg" alt="background" className="w-full h-full object-cover"/>
    </div>
  )
}

export default Background