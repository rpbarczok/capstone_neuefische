import type {Note} from "../types/Note.ts";
import type {Species} from "../types/Species.ts";
import {useEffect, useState} from "react";
import {useContextThrowUndefined} from "../contexts/contextUndefined.ts";
import {LoadingContext} from "../contexts/LoadingContext.ts";
import axios from "axios";
import type {SpeciesCreation} from "../types/SpeciesCreation.ts";

export default function useSpecies(addNotes: (note: Note)=> void)
    : [Species[], (species: SpeciesCreation) => void, (species: Species) => void, (species: Species) => void]
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
            } else {
                setIsLoading(false)
                addNotes({message: " Es ist nicht gelungen, eine neue Spezies anzulegen.", variant: "success"})
            }
        } catch (e) {
            setIsLoading(false)
                addNotes({message: "Es ist nicht gelungen, eine neues Spezies anzulegen: " + e, variant: "danger"})
        }
    }

    async function updateSpecies(species: Species) {
        setIsLoading(true)
        try {
            const response = await axios.put("api/species/"+species.id, species)
            setIsLoading(false)
            void getSpecies()
            addNotes({message: "Die Spezies " + response.data.genus + " wurde erfolgreich upgedated.", variant: "success"})
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist nicht gelungen, die Spezies anzulegen: " + e, variant: "danger"})
        }
    }

    async function deleteSpecies (species: Species) {
        setIsLoading(true)
        try {
            await axios.delete("/api/species/"+species.id)
            setIsLoading(false)
            void getSpecies()
            addNotes({message: "Spezies " + species.genus + " wurde erfolgreich gelöscht.", variant: "success"})
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist nicht gelungen, die Species " + species.genus + " zu löschen:" + e, variant: "danger" })
        }
}

    return [speciesList, addSpecies, updateSpecies, deleteSpecies];
}