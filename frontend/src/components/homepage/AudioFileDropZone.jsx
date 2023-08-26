import React, { useEffect, useState }  from 'react'

import InfoButton from '../utils/InfoButton';
import { INFO_CONTINUE_TO_ANALYSE_AUDIO } from '../../utils/infoTexts';
import { useNavigate } from 'react-router-dom';

const AudioFileDropZone = () => {
    const navigate = useNavigate();

    // readyState: 0 - no file selected;
    //             1 - loader while uploading file;
    //             2 - file uploaded;
    //             3 - error;
    const [audioFileMetadata, setAudioFileMetadata] = useState({
        name: '',
        readyState: 0,
        styles: {
            borderColorClass: 'border-primary',
            borderStyleClass: '',
            backgroundColorClass: 'hover:bg-white/30',
            textColorClass: 'text-primary',
        }
    });
    
    // Handle file upload
    useEffect(() => {
        if (document.getElementById("audioFileUpload")) {
            document.getElementById('audioFileUpload').addEventListener('change', (e) => audioFileUploadChange(e));
        }
    
        return () => {
            if (document.getElementById("audioFileUpload")) {
                document.getElementById('audioFileUpload').removeEventListener('change', (e) => audioFileUploadChange(e));
            }
        }
    
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    

    // Handle drag & drop
    useEffect(() => {
        if (document.getElementById("audioFileDropZone")) {
            document.getElementById("audioFileDropZone").addEventListener('dragenter', (e) => dragEnter(e));
            document.getElementById("audioFileDropZone").addEventListener('dragleave', (e) => dragLeave(e));
            document.getElementById("audioFileDropZone").addEventListener('drop', (e) => drop(e));
        }
    
        return () => {
            if (document.getElementById("audioFileDropZone")) {
                document.getElementById("audioFileDropZone").removeEventListener('dragenter', (e) => dragEnter(e));
                document.getElementById("audioFileDropZone").removeEventListener('dragleave', (e) => dragLeave(e));
                document.getElementById("audioFileDropZone").removeEventListener('drop', (e) => drop(e));
            }
        }
    
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [audioFileMetadata.styles])
    
    return (
        <div className="px-6 lg:px-0">
            <div id="audioFileDropZone" className={`relative w-full lg:w-1/3 h-[300px] mx-auto mt-16 lg:mt-20 cursor-pointer ${audioFileMetadata.styles.backgroundColorClass} transition-all duration-300 rounded-xl`}>
                <input id="audioFileUpload" type="file" accept="audio/*" className="opacity-0 cursor-pointer absolute inset-0"/>

                <div className={`w-full h-full flex justify-center items-center p-4 border-2 ${audioFileMetadata.styles.borderColorClass} ${audioFileMetadata.styles.borderStyleClass} transition-all duration-300 rounded-xl`}>
                    <div className={`${audioFileMetadata.styles.textColorClass} p-8 lg:px-40 flex flex-col gap-2 items-center`}>
                    {
                        audioFileMetadata.readyState === 0 ? (
                        <>
                            <img src="/images/icon-sound.png" className="w-12" alt="icon sound"/>
                            <p className="heading--5 text-center">Drag & Drop or Click<br/>to Upload Audio</p>
                        </>
                        ) : audioFileMetadata.readyState === 1 ? (
                        <>
                            <img src="/images/icon-spinner.png" className="w-12 animate-spin" alt="icon spinner"/>
                        </>
                        ) : audioFileMetadata.readyState === 2 ? (
                        <>
                            <div className="absolute top-6 right-8 opacity-50 rounded-full bg-white/50 h-10 w-10 flex justify-center items-center transition-all duration-300 hover:opacity-100 hover:bg-white/80" 
                                onClick={(e) => deleteUploadedAudioFile(e)}>
                                ❌
                            </div>
                            <img src="/images/icon-sound.png" className="w-12" alt="icon sound"/>
                            <p className="heading--5 text-center">{audioFileMetadata.name}</p>

                            <div className="mt-4">
                                <InfoButton infoText={INFO_CONTINUE_TO_ANALYSE_AUDIO}/>
                            </div>
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

                {audioFileMetadata.readyState === 2 && (
                    <div className={`mt-8 flex justify-center`}>
                        <button type="button" className="--button button--success" onClick={sendAudioForProcessing}>Analyse Audio →</button>
                    </div>
                )}
            </div>
        </div>
    )

    function sendAudioForProcessing(e) {
        // Redirect to audio processing page
        navigate("/processing");
    }

    // Handle user wanting to remove uploaded audio file
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
    
    // Add handler functions for uploading audio file;
    // onloadstart, onload, onerror
    // 
    // Also handle if selected file is not audio file
    function audioFileUploadChange(e) {
        let audioFile = e.target.files[0]
    
        // Handle if audio file is missing or selected file is not audio file
        if (audioFile && audioFile.type.startsWith('audio/')) {
          const reader = new FileReader();
    
          try {
            reader.readAsDataURL(audioFile);
    
            // See readyState meaning at the beginning of this file
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
    
            // See readyState meaning at the beginning of this file
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
    
            // See readyState meaning at the beginning of this file
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
            // File is not audio file or something went wrong when selecting a file.
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
    
    // Dashed border when dragging file over drop zone
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
    
    // Reset input value for file upload when dropping a new file to upload.
    // Then, the input field will get the new file.
    function drop(e) {
        document.getElementById('audioFileUpload').value = "";
    }
}

export default AudioFileDropZone