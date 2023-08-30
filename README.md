# TASER (Talk Analysis & Sound Extraction Resource)

<br/>

### To Do:

- General:
    - [ ] Send and receive Audio file from input to backend

<br/>

- Backend:
    - [ ] Timestamp Created on taser_user & audio_transcription tables.
    - [ ] Don't allow multiple users with same username to be registered.
    - [ ] Error handling with error messages on controllers, errors will be displayed on the front end.

<br/>

- Frontend:
    - [ ] -- Login Page
    - [ ] -- Register Page

<br/>

- Ideas:
    - [ ] After click on 'Analyse Audio' button, a loading screen:
        - While audio is uploading to server, 'Please don't refresh the page' message is displayed
        - Once audio is loaded, ideally backend should send a unique identifier (can be added to URL in frontend) for this audio file, so that even if the page is refreshed, we can still get the results.
        - Maybe query backend API every couple of seconds for status update?
