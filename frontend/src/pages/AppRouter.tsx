import {Route, Routes} from "react-router-dom";
import AnimalPage from "./AnimalPage.tsx";
import TerrariumPage from "./TerrariumPage.tsx";
import HomePage from "./HomePage.tsx";
import SpeciesPage from "./SpeciesPage.tsx";
import useAnimals from "../hooks/useAnimals.ts";
import useSpecies from "../hooks/useSpecies.ts";
import type {Note} from "../types/Note.ts";
import useTerraria from "../hooks/useTerraria.ts";

type RouterProps = {
    addNote: (note: Note) => void
}

export default function AppRouter({addNote}: RouterProps) {
    const [animalList, addAnimal, updateAnimal, deleteAnimal] = useAnimals(addNote)
    const [speciesList, addSpecies, updateSpecies, deleteSpecies] = useSpecies(addNote)
    const [terrariumList, addTerrarium, updateTerrarium, deleteTerrarium] = useTerraria(addNote)

    return (
        <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/animals" element={<AnimalPage
                animalList={animalList}
                addAnimal={addAnimal}
                updateAnimal={updateAnimal}
                deleteAnimal={deleteAnimal}
                speciesList={speciesList}
                terrariumList={terrariumList}
            />}/>
            <Route path="/terraria" element={<TerrariumPage
                terrariumList={terrariumList}
                addTerrarium={addTerrarium}
                updateTerrarium={updateTerrarium}
                deleteTerrarium={deleteTerrarium}
            />}/>
            <Route path="/species" element={<SpeciesPage
                speciesList={speciesList}
                addSpecies={addSpecies}
                updateSpecies={updateSpecies}
                deleteSpecies={deleteSpecies}/>} />
        </Routes>

    )
}