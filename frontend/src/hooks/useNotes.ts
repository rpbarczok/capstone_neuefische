import type {Note} from "../types/Note.ts";
import {useCallback, useState} from "react";


export default function useNotifier(): [Note[], (note: Note) => void, (note: Note) => void] {
    const [notes, setNotes] = useState<Note[]>([])

    const removeNote = useCallback((note: Note) => {
        setNotes(a => a.filter(n => n !== note))
    }, [setNotes])

    const addNote = useCallback((note: Note) => {
        setNotes(a => [
                ...a,
                note
            ]
        )
        
        setTimeout(() => removeNote(note), 5_000)
        
    }, [removeNote])

    return [notes, addNote, removeNote]
}