import type {Animal} from "../../types/Animal.ts";
import {Button, Modal} from "react-bootstrap";


type AnimalModalDeleteProps = {
    animal: Animal,
    deleteAnimal: (animal: Animal) => void,
    show: boolean,
    setShow: (show: boolean) => void
}

export default function AnimalModalDelete ({animal, deleteAnimal, show, setShow}: AnimalModalDeleteProps) {

    function handleDelete() {
        deleteAnimal(animal)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Tier entfernen</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                Bis du sicher, dass du {animal.name} löschen möchtest?
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