import {useState} from "react";
import type {Terrarium} from "../../types/Terrarium.ts";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";

type TerrariumModalUpdateProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    updateTerrarium: (terrarium: Terrarium) => void,
    terrarium: Terrarium
}

export default function TerrariumModalUpdate({show, setShow, updateTerrarium, terrarium}: TerrariumModalUpdateProps) {

    const originalTerrarium = terrarium
    const [updatedTerrarium, setUpdatedTerrarium] = useState(terrarium)

    function handleChangeName(value: string) {

        setUpdatedTerrarium(
            {
                ...updatedTerrarium,
                name: value
            }
        )
    }

    function handleChangeHeight(value: number) {

        setUpdatedTerrarium(
            {
                ...updatedTerrarium,
                height: value
            }
        )
    }

    function handleChangeWidth(value: number) {

        setUpdatedTerrarium(
            {
                ...updatedTerrarium,
                width: value
            }
        )
    }

    function handleChangeDepth(value: number) {

        setUpdatedTerrarium(
            {
                ...updatedTerrarium,
                depth: value
            }
        )
    }

    function undo() {
        setUpdatedTerrarium(originalTerrarium)
    }

    function submitUpdatedTerrarium() {
        updateTerrarium(updatedTerrarium)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={()=> setShow(false)}>
            <Modal.Header>
                <Modal.Title>Terrarium verändern</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control onChange={(e) => handleChangeName(e.target.value)} type="text" value={updatedTerrarium.name}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumHeight">
                                <Form.Label>Höhe (in cm)</Form.Label>
                                <Form.Control onChange={(e) => handleChangeHeight(Number(e.target.value))} type="text" value={updatedTerrarium.height}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumWidth">
                                <Form.Label>Breite (in cm)</Form.Label>
                                <Form.Control onChange={(e) => handleChangeWidth(Number(e.target.value))} type="text" value={updatedTerrarium.width}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumDepth">
                                <Form.Label>Tiefe (in cm)</Form.Label>
                                <Form.Control onChange={(e) => handleChangeDepth(Number(e.target.value))} type="text" value={updatedTerrarium.depth}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={() => submitUpdatedTerrarium()}>
                    Speichern
                </Button>
                <Button variant="secondary" onClick={() => undo()}>
                    Undo
                </Button>
                <Button variant="secondary" onClick={() => setShow(false)}>
                    Abbrechen
                </Button>
            </Modal.Footer>
        </Modal>
    )
}