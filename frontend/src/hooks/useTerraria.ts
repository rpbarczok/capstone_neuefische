import type {Note} from "../types/Note.ts";
import type { Terrarium } from "../types/Terrarium.ts";
import type {TerrariumCreation} from "../types/TerrariumCreation.ts";
import {useEffect, useState} from "react";
import {useContextThrowUndefined} from "../contexts/contextUndefined.ts";
import {LoadingContext} from "../contexts/LoadingContext.ts";
import axios from "axios";

export default function useTerraria(addNotes: (note: Note)=> void)
    : [Terrarium[], (terrarium: TerrariumCreation) => void, (terrarium: Terrarium) => void, (terrarium: Terrarium) => void]
{
    const [terrariumList, setTerrariumList] = useState<Terrarium[]>([])
    const {setIsLoading } = useContextThrowUndefined<{ isLoading: boolean, setIsLoading: (isLoading: boolean) => void }> (LoadingContext)

    useEffect(() => {
        void getTerraria()
    }, [])

    async function getTerraria() {
        setIsLoading(true)
        try {
            const response = await axios.get("api/terraria", {headers: {"X-Clacks-Overhead": "GNU Terry Pratchett"}})
            setTerrariumList(response.data)
            setIsLoading(false)
        } catch {
            addNotes({message: "Die Abfrage nach allen Terrarien ist schiefgegangen", variant: "danger"})
            setIsLoading(false)
        }
    }

    async function addTerrarium(terrarium: TerrariumCreation){
        setIsLoading(true)
        try {
            const response = await axios.post("api/terraria", terrarium, {headers: {"X-Clacks-Overhead": "GNU Terry Pratchett"}})
            if (response.data !== null) {
                setIsLoading(false)
                void getTerraria()
                addNotes({message: response.data.name + " wurde erfolgreich angelegt.", variant: "success"})
            } else {
                setIsLoading(false)
                addNotes({message: " Es ist nicht gelungen, ein neues Terrarium anzulegen.", variant: "success"})
            }
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist nicht gelungen, ein neues Terrarium anzulegen: " + e, variant: "danger"})
        }
    }

    async function updateTerrarium(terrarium: Terrarium) {
        setIsLoading(true)
        try {
            const response = await axios.put("api/terraria/"+terrarium.id, terrarium, {headers: {"X-Clacks-Overhead": "GNU Terry Pratchett"}})
            setIsLoading(false)
            void getTerraria()
            addNotes({message: "Das Terrarium " + response.data.name + " wurde erfolgreich upgedated.", variant: "success"})
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist nicht gelungen, das Terrarium upzudaten: " + e, variant: "danger"})
        }
    }

    async function deleteTerrarium (terrarium: Terrarium) {
        setIsLoading(true)
        try {
            await axios.delete("/api/terraria/"+terrarium.id, {headers: {"X-Clacks-Overhead": "GNU Terry Pratchett"}})
            setIsLoading(false)
            void getTerraria()
            addNotes({message: "Das Terrarium " + terrarium.name + " wurde erfolgreich gelöscht.", variant: "success"})
        } catch (e) {
            setIsLoading(false)
            addNotes({message: "Es ist nicht gelungen, das Terrarium " + terrarium.name + " zu löschen:" + e, variant: "danger" })
        }
    }

    return [terrariumList, addTerrarium, updateTerrarium, deleteTerrarium];
}