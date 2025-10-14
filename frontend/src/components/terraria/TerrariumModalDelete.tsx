import type {Terrarium} from "../../types/Terrarium.ts";
import {Button, Modal} from "react-bootstrap";

type TerrariumModalDeleteProps = {
    terrarium: Terrarium,
    deleteTerrarium: (terrarium: Terrarium) => void,
    show: boolean,
    setShow: (show: boolean) => void
}

export default function TerrariumModalDelete ({terrarium, deleteTerrarium, show, setShow}: TerrariumModalDeleteProps) {
    function handleDelete() {
        deleteTerrarium(terrarium)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Terrarium entfernen</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                Bis du sicher, dass du das Terrarium {terrarium.name} löschen möchtest?
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