import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import Background from "./components/shared/Background";
import Header from "./components/shared/Header";
import PageContent from "./components/shared/PageContent";

import { getCookie } from "./utils/functions/cookies";

import { setAccount, setLoggedIn } from "./redux/reducers/accountSlice";

function App() {
    const dispatch = useDispatch();

    const account = useSelector(state => state?.account);
    
    useEffect(() => {
        let bearerToken = getCookie("bearerToken");
        let username = getCookie("username");
        if (typeof bearerToken === "string" && bearerToken.length > 0 && typeof username === "string" && username.length > 0) {
            if (!account.loggedIn) {
                dispatch(setLoggedIn(true));
                dispatch(setAccount({ username: username }));
            }
        }
        else {
            if (account.loggedIn) {
                dispatch(setLoggedIn(false));
                dispatch(setAccount({ username: "" }));
            }
        }
        
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    return (
        <div>

            <Background/>

            <Header/>

            <div className="w-full h-40 lg:h-48"></div>

            <PageContent/>

        </div>
    );
}

export default App;
