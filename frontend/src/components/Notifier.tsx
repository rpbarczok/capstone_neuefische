import { Alert } from 'react-bootstrap'
import type {Note} from "../types/Note.ts";

type NoteProps = {
    notes: Note[]
    removeNote: (note: Note) => void
}

export default function Notifier ({ notes, removeNote }: NoteProps) {

    const handleClose = (note: Note) => {
        removeNote(note)
    }

    let i = 0
    return notes.map(note => {
            i += 1
            return (
                <Alert key={i} variant={note.variant} onClose={() => handleClose(note)} dismissible>{note.message}</Alert>
            )
        }
    )
}