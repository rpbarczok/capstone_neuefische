import type {Note} from "../types/Note.ts";
import type {Species} from "../types/Species.ts";
import {useEffect, useState} from "react";
import {useContextThrowUndefined} from "../contexts/contextUndefined.ts";
import {LoadingContext} from "../contexts/LoadingContext.ts";
import axios from "axios";

export default function useSpecies(addNotes: (note: Note)=> void)
    : [Species[], () => void]
{
    const [speciesList, setSpeciesList] = useState<Species[]>([])
    const {setIsLoading } = useContextThrowUndefined<{ isLoading: boolean, setIsLoading: (isLoading: boolean) => void }> (LoadingContext)

    useEffect(() => {
        void getSpecies()
    }, [])

    async function getSpecies() {
        setIsLoading(true)
        try {
            const response = await axios.get("api/animals")
            setSpeciesList(response.data)
            setIsLoading(false)
        } catch {
            addNotes({message: "Die Abfrage nach allen Spezies ist schiefgegangen", variant: "danger"})
            setIsLoading(false)
        }
    }

//    const updateSpecies (species: Species): Species {}

//    const deleteSpecies (id: number) {}

    return [speciesList, getSpecies];
}