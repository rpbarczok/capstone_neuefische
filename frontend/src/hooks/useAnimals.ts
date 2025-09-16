import { useEffect, useState} from "react";
import type {Animal} from "../types/Animal.ts";
import {LoadingContext} from "../contexts/LoadingContext.ts";
import axios from "axios";
import type {Note} from "../types/Note.ts";
import {useContextThrowUndefined} from "../contexts/contextUndefined.ts";
import type {AnimalCreation} from "../types/AnimalCreation.ts";

export default function useAnimals(addNotes: (note: Note)=> void)
    : [Animal[], () => void, (animal: AnimalCreation) => void]
{
    const [animalList, setAnimalList] = useState<Animal[]>([])
    const {setIsLoading } = useContextThrowUndefined<{ isLoading: boolean, setIsLoading: (isLoading: boolean) => void }> (LoadingContext)

    useEffect(() => {
        void getAnimals()
    }, [])

    async function getAnimals() {
        setIsLoading(true)
        try {
            const response = await axios.get("api/animals")
            setAnimalList(response.data)
            setIsLoading(false)
        } catch {
            setIsLoading(false)
            addNotes({message: "Die Abfrage nach allen Tieren ist schiefgegangen", variant: "danger"})
        }
    }

    async function addAnimal(animal: AnimalCreation) {
        setIsLoading(true)
        try {
            const response = await axios.post("api/animals", animal)
            if (response.data !== null) {
                setIsLoading(false)
                void getAnimals()
                addNotes({message: response.data.name + " wurde erfolgreich angelegt.", variant: "success"})
            }
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist nicht gelungen, eine neues Tier anzulegen: " + e, variant: "danger"})
        }
    }

//    const updateAnimals (animal: Animal): Animal {}

//    const deleteAnimals (id: number) {}

    return [animalList, getAnimals, addAnimal];
}
