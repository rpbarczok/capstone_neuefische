import type {Species} from "../../types/Species.ts";
import {Button, Modal} from "react-bootstrap";


type SpeciesModalDeleteProps = {
    species: Species,
    deleteSpecies: (species: Species) => void,
    show: boolean,
    setShow: (show: boolean) => void
}

export default function SpeciesModalDelete ({species, deleteSpecies, show, setShow}: SpeciesModalDeleteProps) {
    function handleDelete() {
        deleteSpecies(species)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={() => setShow(false)}>
        <Modal.Header closeButton>
            <Modal.Title>Spezies entfernen</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            Bis du sicher, dass du die Spezies {species.genus} löschen möchtest?
        </Modal.Body>
        <Modal.Footer>
            <Button variant="secondary" onClick={() => setShow(false)}>
                Nein, nicht  löschen.
            </Button>
            <Button variant="primary" onClick={() => handleDelete()}>
                Ja, löschen
            </Button>
        </Modal.Footer>
    </Modal>

    )
}