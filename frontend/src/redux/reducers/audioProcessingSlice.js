import { createSlice } from '@reduxjs/toolkit'

export const audioProcessingSlice = createSlice({
    name: 'audio-processing',
    initialState: {
        // 0 - (INACTIVE) server is receiving the audio file;
        // 1 - server is processing the audio file;
        // 2 - server has finished processing the audio file;
        // 3 - server has encountered an error while processing the audio file;
        audioProcessingStatus: 2,
    },
    reducers: {
        setAudioProcessingStatus: (state, action) => {
            state.audioProcessingStatus = action.payload;
        }
    }
})

export const { setAudioProcessingStatus } = audioProcessingSlice.actions;

export default audioProcessingSlice.reducer;
