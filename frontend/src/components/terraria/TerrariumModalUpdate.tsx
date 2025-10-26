import {type FormEvent, useState} from "react";
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
    const [validated, setValidated] = useState(false)
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

    function handleClose  () {
        setValidated(false)
        setShow(false)
    }

    function submitUpdatedTerrarium(e: FormEvent<HTMLFormElement>, form: HTMLFormElement) {
        e.preventDefault()
        if (!form.checkValidity()) {
            setValidated(true)
        } else {
            updateTerrarium(updatedTerrarium)
            setValidated(false)
            setShow(false)
        }
    }

    return (
        <Modal show={show} onHide={()=> setShow(false)}>
            <Form noValidate validated={validated} onSubmit={(e)=> submitUpdatedTerrarium(e, e.currentTarget)}>
                <Modal.Header>
                    <Modal.Title>Terrarium verändern</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeName(e.target.value)} type="text" value={updatedTerrarium.name}>
                                </Form.Control>
                                <Form.Control.Feedback type="invalid">Bitte einen Namen für das Terrarium eingeben</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumHeight">
                                <Form.Label>Höhe (in cm)</Form.Label>
                                <Form.Control required  onChange={(e) => handleChangeHeight(Number(e.target.value))} type="number" value={updatedTerrarium.height}>
                                </Form.Control>
                                <Form.Control.Feedback type="invalid">Bitte die Höhe in cm eintragen</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumWidth">
                                <Form.Label>Breite (in cm)</Form.Label>
                                <Form.Control
                                    onChange={(e) => handleChangeWidth(Number(e.target.value))}
                                    type="number" value={updatedTerrarium.width}>
                                </Form.Control>
                                <Form.Control.Feedback type="invalid">Bitte die Breite in cm eintragen</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumDepth">
                                <Form.Label>Tiefe (in cm)</Form.Label>
                                <Form.Control
                                    onChange={(e) => handleChangeDepth(Number(e.target.value))}
                                    type="number"
                                    value={updatedTerrarium.depth}>
                                </Form.Control>
                                <Form.Control.Feedback type="invalid">Bitte die Tiefe in cm eintragen</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                    </Row>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" type="submit">
                        Speichern
                    </Button>
                    <Button variant="secondary" onClick={() => undo()}>
                        Undo
                    </Button>
                    <Button variant="secondary" onClick={() => handleClose()}>
                        Abbrechen
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    )
}