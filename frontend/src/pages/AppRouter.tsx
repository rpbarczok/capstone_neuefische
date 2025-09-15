import {Route, Routes} from "react-router-dom";
import AnimalPage from "./AnimalPage.tsx";
import TerrariumPage from "./TerrariumPage.tsx";
import HomePage from "./HomePage.tsx";
import type {Animal} from "../types/Animal.ts";
import type {Species} from "../types/Species.ts";
import type {AnimalCreation} from "../types/AnimalCreation.ts";

type RouterProps = {
    animalList: Animal[],
    getAnimals: () => void,
    addAnimal: (animal: AnimalCreation) => void,
    speciesList: Species[],
    getSpecies: () => void,
}

export default function AppRouter ({animalList, getAnimals, addAnimal, speciesList, getSpecies}: RouterProps) {
    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/animals" element = {<AnimalPage
                                                animalList={animalList}
                                                getAnimals={getAnimals}
                                                addAnimal={addAnimal}
                                                speciesList={speciesList}
                                                getSpecies={getSpecies}
                                            />}/>
            <Route path="/terraria" element = {<TerrariumPage/>}/>
        </Routes>

    )
}