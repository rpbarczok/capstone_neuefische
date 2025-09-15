import type {Note} from "../types/Note.ts";
import type {Species} from "../types/Species.ts";
import {useEffect, useState} from "react";
import {useContextThrowUndefined} from "../contexts/contextUndefined.ts";
import {LoadingContext} from "../contexts/LoadingContext.ts";
import axios from "axios";
import type {SpeciesCreation} from "../types/SpeciesCreation.ts";

export default function useSpecies(addNotes: (note: Note)=> void)
    : [Species[], () => void, (species: SpeciesCreation) => void]
{
    const [speciesList, setSpeciesList] = useState<Species[]>([])
    const {setIsLoading } = useContextThrowUndefined<{ isLoading: boolean, setIsLoading: (isLoading: boolean) => void }> (LoadingContext)

    useEffect(() => {
        void getSpecies()
    }, [])

    async function getSpecies() {
        setIsLoading(true)
        try {
            const response = await axios.get("api/species")
            setSpeciesList(response.data)
            setIsLoading(false)
        } catch {
            addNotes({message: "Die Abfrage nach allen Spezies ist schiefgegangen", variant: "danger"})
            setIsLoading(false)
        }
    }

    async function addSpecies(species: SpeciesCreation){
        setIsLoading(true)
        try {
            const response = await axios.post("api/species", species)
            if (response.data !== null) {
                setIsLoading(false)
                void getSpecies()
                addNotes({message: response.data.genus + " wurde erfolgreich angelegt.", variant: "success"})
            }
        } catch (e) {
            setIsLoading(false)
                addNotes({message: "Es ist nicht gelungen, eine neues Spezies anzulegen: " + e, variant: "danger"})
        }
    }
//    const updateSpecies (species: Species): Species {}

//    const deleteSpecies (id: number) {}

    return [speciesList, getSpecies, addSpecies];
}