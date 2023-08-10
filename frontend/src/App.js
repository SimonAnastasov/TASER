import Homepage from "./components/homepage/Main";
import Background from "./components/shared/Background";
import Header from "./components/shared/Header";

function App() {
  return (
    <div>

      <Background/>

      <Header/>

      <div className="w-full h-40 lg:h-48"></div>
      <Homepage/>

    </div>
  );
}

export default App;
