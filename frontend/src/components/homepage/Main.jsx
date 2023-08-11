import React, { useEffect } from 'react'

const Homepage = () => {
  const [audioFileMetadata, setAudioFileMetadata] = React.useState({
    name: '',
    readyState: 0,
    styles: {
      borderColorClass: 'border-primary',
      backgroundColorClass: 'hover:bg-white/30',
      textColorClass: 'text-primary',
    }
  });

  function clickAudioFileUpload(e) {
    document.getElementById('audioFileUpload').click();
  }

  useEffect(() => {
    let audioFileUpload = document.getElementById('audioFileUpload')

    function audioFileUploadChange(e) {
      let audioFile = e.target.files[0]

      if (audioFile) {
        const reader = new FileReader();

        try {
          reader.readAsDataURL(audioFile);

          reader.onloadstart = function(e) {
            console.log(e);
            setAudioFileMetadata({
              ...audioFileMetadata,
              readyState: 1,
              styles: {
                borderColorClass: 'border-gray-700',
                backgroundColorClass: 'bg-gray-300/10 hover:bg-auto',
                textColorClass: 'text-gray-700',
              }
            })
          }
  
          reader.onload = function(e) {
            console.log(e);
            setAudioFileMetadata({
              ...audioFileMetadata,
              name: audioFile.name,
              readyState: 2,
              styles: {
                borderColorClass: 'border-green-700',
                backgroundColorClass: 'bg-green-300/10 hover:bg-auto',
                textColorClass: 'text-green-700',
              }
            })
          }
        } catch (exception) {
          return ;
        }
      }
    }

    audioFileUpload.addEventListener('change', (e) => audioFileUploadChange(e))

    return () => {
      audioFileUpload.removeEventListener('change', (e) => audioFileUploadChange(e))
    }
  }, [])

  return (
    <div>
        <div className="tailwindClassesLoader hidden"></div>

        <h1 className="heading--3 text-center">Talk Analysis & Sound Extraction Resource</h1>
        <p className="--small-text text-center mt-6">Free Version TASER-F</p>

        <div className="px-6 lg:px-0">
          <div className={`relative w-full lg:w-1/3 h-[300px] mx-auto mt-16 lg:mt-20 cursor-pointer ${audioFileMetadata.styles.backgroundColorClass} transition-all duration-300 rounded-xl`} onClick={(e) => clickAudioFileUpload(e)}>
            <input id="audioFileUpload" type="file" accept="audio/*" className="hidden"/>

            <div className={`w-full h-full flex justify-center items-center p-4 border-2 ${audioFileMetadata.styles.borderColorClass} transition-all duration-300 rounded-xl`}>
              <div className={`${audioFileMetadata.styles.textColorClass} p-20 lg:px-40 flex flex-col gap-2 items-center`}>
              {
                audioFileMetadata.readyState === 0 ? (
                  <>
                    <img src="/images/icon-sound.png" className="w-12" alt="icon sound"/>
                    <p className="heading--5 text-center">Drag & Drop or Click<br/>to Upload Audio</p>
                  </>
                ) : audioFileMetadata.readyState === 1 ? (
                  <>
                    <img src="/images/icon-spinner.png" className="w-12 animate-spin" alt="icon sound"/>
                  </>
                ) : audioFileMetadata.readyState === 2 ? (
                  <>
                    <img src="/images/icon-sound.png" className="w-12" alt="icon sound"/>
                    <p className="heading--5 text-center">{audioFileMetadata.name}</p>
                  </>
                ) : (<></>)
              }
              </div>
            </div>
          </div>
        </div>
    </div>
  )
}

export default Homepage