# TASER frontend testing

## Link to the tests
[TASER frontend tests](https://github.com/SimonAnastasov/TASER/tree/main/frontend/src/tests)

## Running the tests
To run the tests:
1. Install the dependencies: `npm install` from the /frontend directory
2. Run the tests: `npm test` from the /frontend directory

## Technologies used: 
- [Jest](https://jestjs.io/) for testing
- [React Testing Library](https://testing-library.com/docs/react-testing-library/intro/) for testing React components
- [Mock Service Worker](https://mswjs.io/) for mocking API calls

## Methodology

In the /tests directory, each component has its own *.test.jsx file. These files contain the tests for the component. The tests are organized into describe blocks.
The store is initialized with the same state that the component would receive from the actual store. The store is mutated with the same dispatch function, which is used to test the component's interaction with the store in production.
The tests are written using the React Testing Library. This library provides a set of functions that allow us to interact with the component in the same way that a user would. 

We also have testing data set up in the /tests/utils/testingData.js file. This data is used to mock the API responses from the backend. 

The login functionality is tested with the Mock Service Worker. This library allows us to mock the API calls that are made to the backend. We can specify the response that the API call should return, and we can test that the component behaves as expected when it receives that response. 

## Jest + React Testing Library 
```javascript
// frontend/src/tests/GlobalAnalysisResults.test.jsx
describe ('Global Analysis Results', () => {
    const testStore = store
    const produceGlobalAnalysisResultsComponent = () =>
    render(
    <Provider store={testStore}>
        <Router>
         <GlobalAnalysisResults/>
        </Router>
    </Provider>
    );

    test('global analysis results component shows analysis', () => {
        const analysis = TEST_ANALYSIS_RESULT_RESPONSE
        const analysisResult = analysis?.transcription?.id ? JSON.parse(analysis?.transcription?.text) : null;
        testStore.dispatch(setAnalysisResult(analysis.transcription))
        produceGlobalAnalysisResultsComponent();
        let expectedSentiment = analysisResult?.Global_sentiment[0].toUpperCase() + analysisResult?.Global_sentiment.slice(1)
        let expectedSentimentText = "Sentiment: " + expectedSentiment
        const sentiment = screen.getByText(expectedSentimentText);
        expect(sentiment).toBeInTheDocument();
    }); 

    test('global analysis results component shows no analysis', () => {
        testStore.dispatch(setAnalysisResult({}))
        produceGlobalAnalysisResultsComponent();
        const sentiment = screen.queryByText(/Sentiment/i);
        expect(sentiment).not.toBeInTheDocument();
    });

    
})
```
In this example, we are testing the GlobalAnalysisResults component. We are testing that the component renders the correct sentiment when the analysis is present, and that the component does not render the sentiment when the analysis is not present.

We are mocking the store with the testStore variable. We are dispatching the setAnalysisResult action to the store, which is the same action that is dispatched in production. 
There is also a produceGlobalAnalysisResultsComponent function, which renders the component with the mocked store. Each test component has a similar function.

## Mock Service Worker
```javascript
// frontend/src/tests/Login.test.jsx
test('Test logging in', async () => {
        // find username and password fields and input values
        produceLoginComponent();
        const username = screen.getByPlaceholderText("Username");
        const password = screen.getByPlaceholderText("Password");
        const usernameValue = "testuser"
        const passwordValue = "testuser"
        // input values
        username.value = usernameValue
        password.value = passwordValue
        // find submit button and click
        await act(async () => {
            const submitButton = screen.getByRole('submit-button');
            submitButton.click()
        })
        // check if logged in
        const state = testStore.getState();
        expect(state.account?.loggedIn).toBe(true);
    });
```
In this example, we are testing the login functionality. We are mocking the API call to the backend, and we are testing that the user is logged in when the correct credentials are entered. Notice the test code doesn't feature any store mutations, because the login functionality is handled by the mock backend.

```javascript
// frontend/src/tests/utils/setupTests.js
import { server } from '../../mocks/server.js'
// Establish API mocking before all tests.
beforeAll(() => server.listen())

// Reset any request handlers that we may add during the tests,
// so they don't affect other tests.
afterEach(() => server.resetHandlers())

// Clean up after the tests are finished.
afterAll(() => server.close())
```
This is where we set up the mock backend for use with Jest.

```javascript
// frontend/src/mocks/handlers.js
import { rest } from 'msw'

const baseUrl = 'http://localhost:8080/api'

export const handlers = [
    rest.post(baseUrl + '/login', (req, res, ctx) => {
        const { username, password } = req.body
        if (username == 'testuser' && password == 'testuser') {
            return res(
                ctx.status(200),
                ctx.json({
                    bearerToken: 'mocked_user_token',
                }),
            )
        } else {
            return res(
                ctx.status(400),
                ctx.json({
                    "error": true,
                    "message": "You entered invalid credentials."
                }),
            )
        }
    }),


```
The handlers file is where we specify the response that the API call should return. In this example, we are mocking the login API call. If the username and password are correct, the API call returns a bearer token. If the username and password are incorrect, the API call returns an error message.
