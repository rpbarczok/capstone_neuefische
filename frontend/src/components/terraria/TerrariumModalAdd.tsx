import type {TerrariumCreation} from "../../types/TerrariumCreation.ts";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import {useState} from "react";

type TerrariumModalAppProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    addTerrarium: (terrarium: TerrariumCreation) => void
}

export default function TerrariumModalAdd({show, setShow, addTerrarium}: TerrariumModalAppProps) {
    const emptyTerrariumCreation: TerrariumCreation={name: "", height: 0, width: 0, depth: 0}
    const [newTerrarium, setNewTerrarium] = useState<TerrariumCreation>(emptyTerrariumCreation)

    function handleChangeName(value: string) {

        setNewTerrarium(
            {
                ...newTerrarium,
                name: value
            }
        )
    }

    function handleChangeHeight(value: number) {

        setNewTerrarium (
            {
                ...newTerrarium,
                height: value
            }
        )
    }

    function handleChangeWidth(value: number) {

        setNewTerrarium (
            {
                ...newTerrarium,
                width: value
            }
        )
    }

    function handleChangeDepth(value: number) {

        setNewTerrarium (
            {
                ...newTerrarium,
                depth: value
            }
        )
    }

    function submitNewTerrarium () {
        addTerrarium(newTerrarium)
        setNewTerrarium(emptyTerrariumCreation)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Terrarium hinzufügen</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control onChange={(e) => handleChangeName(e.target.value) } type="text" />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumHeight">
                                <Form.Label>Höhe (in cm)</Form.Label>
                                <Form.Control onChange={(e) => handleChangeHeight(Number(e.target.value)) } type="text" />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumWidth">
                                <Form.Label>Breite (in cm)</Form.Label>
                                <Form.Control onChange={(e) => handleChangeWidth(Number(e.target.value)) } type="text" />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumDepth">
                                <Form.Label>Tiefe (in cm)</Form.Label>
                                <Form.Control onChange={(e) => handleChangeDepth(Number(e.target.value)) } type="text" />
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={() => submitNewTerrarium()}>
                    Abspeichern
                </Button>
                <Button variant="secondary" onClick={() => setShow(false)}>
                    Abbrechen
                </Button>
            </Modal.Footer>
        </Modal>
    )
}