import React, { useEffect } from 'react'

const Homepage = () => {
  const [audioFileMetadata, setAudioFileMetadata] = React.useState({
    name: '',
    readyState: 0,
    styles: {
      borderColorClass: 'border-primary',
      borderStyleClass: '',
      backgroundColorClass: 'hover:bg-white/30',
      textColorClass: 'text-primary',
    }
  });

  useEffect(() => {
    document.getElementById('audioFileUpload').addEventListener('change', (e) => audioFileUploadChange(e));

    return () => {
      document.getElementById('audioFileUpload').removeEventListener('change', (e) => audioFileUploadChange(e));
    }
  }, [])

  useEffect(() => {
    document.getElementById("audioFileDropZone").addEventListener('dragenter', (e) => dragEnter(e));
    document.getElementById("audioFileDropZone").addEventListener('dragleave', (e) => dragLeave(e));
    document.getElementById("audioFileDropZone").addEventListener('drop', (e) => drop(e));

    return () => {
      document.getElementById("audioFileDropZone").removeEventListener('dragenter', (e) => dragEnter(e));
      document.getElementById("audioFileDropZone").removeEventListener('dragleave', (e) => dragLeave(e));
      document.getElementById("audioFileDropZone").removeEventListener('drop', (e) => drop(e));
    }
  }, [audioFileMetadata.styles])

  return (
    <div>
        <div className="tailwindClassesLoader hidden"></div>

        <h1 className="heading--3 text-center">Talk Analysis & Sound Extraction Resource</h1>
        <p className="--small-text text-center mt-6">Free Version TASER-F</p>

        <div className="px-6 lg:px-0">
          <div id="audioFileDropZone" className={`relative w-full lg:w-1/3 h-[300px] mx-auto mt-16 lg:mt-20 cursor-pointer ${audioFileMetadata.styles.backgroundColorClass} transition-all duration-300 rounded-xl`}>
            <input id="audioFileUpload" type="file" accept="audio/*" className="opacity-0 cursor-pointer absolute inset-0"/>

            <div className={`w-full h-full flex justify-center items-center p-4 border-2 ${audioFileMetadata.styles.borderColorClass} ${audioFileMetadata.styles.borderStyleClass} transition-all duration-300 rounded-xl`}>
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
                    <div className="absolute top-6 right-8 opacity-50 rounded-full bg-white/50 h-10 w-10 flex justify-center items-center transition-all duration-300 hover:opacity-100 hover:bg-white/80" 
                          onClick={(e) => deleteUploadedAudioFile(e)}>
                      ❌
                    </div>
                    <img src="/images/icon-sound.png" className="w-12" alt="icon sound"/>
                    <p className="heading--5 text-center">{audioFileMetadata.name}</p>
                  </>
                ) : audioFileMetadata.readyState === 3 ? (
                  <>
                    <img src="/images/icon-sound.png" className="w-12" alt="icon sound"/>
                    <p className="heading--5 text-center">Error occurred<br/>Please try again</p>
                  </>
                ) : (<></>)
              }
              </div>
            </div>
          </div>
        </div>
    </div>
  )

  function deleteUploadedAudioFile(e) {
    e.stopPropagation();

    document.getElementById('audioFileUpload').value = "";

    setAudioFileMetadata({
      ...audioFileMetadata,
      name: '',
      readyState: 0,
      styles: {
        borderColorClass: 'border-primary',
        borderStyleClass: '',
        backgroundColorClass: 'hover:bg-white/30',
        textColorClass: 'text-primary',
      }
    })
  }

  function audioFileUploadChange(e) {
    let audioFile = e.target.files[0]

    if (audioFile && audioFile.type.startsWith('audio/')) {
      const reader = new FileReader();

      try {
        reader.readAsDataURL(audioFile);

        reader.onloadstart = function(e) {
          setAudioFileMetadata({
            ...audioFileMetadata,
            readyState: 1,
            styles: {
              borderColorClass: 'border-gray-700',
              borderStyleClass: '',
              backgroundColorClass: 'bg-gray-300/10 hover:bg-auto',
              textColorClass: 'text-gray-700',
            }
          })
        }

        reader.onload = function(e) {
          setAudioFileMetadata({
            ...audioFileMetadata,
            name: audioFile.name,
            readyState: 2,
            styles: {
              borderColorClass: 'border-green-700',
              borderStyleClass: '',
              backgroundColorClass: 'bg-green-300/10 hover:bg-auto',
              textColorClass: 'text-green-700',
            }
          })
        }

        reader.onerror = function(e) {
          setAudioFileMetadata({
            ...audioFileMetadata,
            readyState: 3,
            styles: {
              borderColorClass: 'border-red-700',
              borderStyleClass: '',
              backgroundColorClass: 'bg-red-300/10 hover:bg-auto',
              textColorClass: 'text-red-700',
            }
          })
        }
      } catch (exception) {
        return ;
      }
    } else {
      document.getElementById('audioFileUpload').value = "";

      setAudioFileMetadata({
        ...audioFileMetadata,
        name: '',
        readyState: 0,
        styles: {
          ...audioFileMetadata.styles,
          borderStyleClass: '',
        }
      })
    }
  }

  function dragEnter(e) {
    e.preventDefault();

    setAudioFileMetadata({
      ...audioFileMetadata,
      styles: {
        ...audioFileMetadata.styles,
        borderStyleClass: 'border-dashed',
      }
    })
  }

  function dragLeave(e) {
    e.preventDefault();
    
    setAudioFileMetadata({
      ...audioFileMetadata,
      styles: {
        ...audioFileMetadata.styles,
        borderStyleClass: '',
      }
    })
  }

  function drop(e) {
    document.getElementById('audioFileUpload').value = "";
  }

}

export default Homepage