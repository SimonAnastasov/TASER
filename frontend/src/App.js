import Background from "./components/shared/Background";
import Header from "./components/shared/Header";
import PageContent from "./components/shared/PageContent";

function App() {
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
