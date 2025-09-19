import { useEffect, useState} from "react";
import type {Animal} from "../types/Animal.ts";
import {LoadingContext} from "../contexts/LoadingContext.ts";
import axios from "axios";
import type {Note} from "../types/Note.ts";
import {useContextThrowUndefined} from "../contexts/contextUndefined.ts";
import type {AnimalCreation} from "../types/AnimalCreation.ts";

export default function useAnimals(addNotes: (note: Note)=> void)
    : [Animal[], (animal: AnimalCreation) => void, (animal: Animal) => void, (animal: Animal) => void] {

    const [animalList, setAnimalList] = useState<Animal[]>([])
    const {setIsLoading} = useContextThrowUndefined<{
        isLoading: boolean,
        setIsLoading: (isLoading: boolean) => void
    }>(LoadingContext)

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

    async function updateAnimal (animal: Animal) {
        setIsLoading(true)
        try{
            const response = await axios.put("api/animals/"+animal.id)
            setIsLoading(false)
            void getAnimals()
            addNotes({message: response.data.name + " wurde refolgreich angelegt", variant: "success"})
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist icht gelungen, " + animal.name + " anzulegen: "+ e, variant: "warning"})
        }
    }

    async function deleteAnimal(animal: Animal) {
        setIsLoading(true)
        try {
            await axios.delete("/api/animals/" + animal.id)
            setIsLoading(false)
            void getAnimals()
            addNotes({message: animal.name + " wurde erfolgreich gelöscht.", variant: "success"})
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist nicht gelungen, die " + animal.name + " zu löschen:" + e, variant: "danger"})
        }
    }

    return [animalList, addAnimal, updateAnimal, deleteAnimal];

}