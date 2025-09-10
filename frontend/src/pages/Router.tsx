import {Route, Routes} from "react-router-dom";
import AnimalPage from "./AnimalPage.tsx";
import TerrariumPage from "./TerrariumPage.tsx";
import HomePage from "./HomePage.tsx";

export default function Router () {
    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/animals" element = {<AnimalPage/>}/>
            <Route path="/terraria" element = {<TerrariumPage/>}/>
        </Routes>

    )
}