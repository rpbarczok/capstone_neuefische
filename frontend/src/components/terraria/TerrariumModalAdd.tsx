import type {TerrariumCreation} from "../../types/TerrariumCreation.ts";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import {type FormEvent, useState} from "react";

type TerrariumModalAppProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    addTerrarium: (terrarium: TerrariumCreation) => void
}

export default function TerrariumModalAdd({show, setShow, addTerrarium}: TerrariumModalAppProps) {
    const emptyTerrariumCreation: TerrariumCreation={name: "", height: 0, width: 0, depth: 0}
    const [newTerrarium, setNewTerrarium] = useState<TerrariumCreation>(emptyTerrariumCreation)
    const [validated, setValidated] = useState(false)

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

    function handleClose () {
        setValidated(false)
        setNewTerrarium (emptyTerrariumCreation)
        setShow(false)
    }

    function submitNewTerrarium (e: FormEvent<HTMLFormElement>, form: HTMLFormElement) {
        e.preventDefault()
        if (!form.checkValidity()) {
            setValidated(true)
        } else {
            addTerrarium(newTerrarium)
            setNewTerrarium(emptyTerrariumCreation)
            setValidated(false)
            setShow(false)
        }
    }

    return (
        <Modal show={show} onHide={()=>handleClose()}>
            <Form noValidate validated={validated} onSubmit={(e)=> submitNewTerrarium(e, e.currentTarget)}>
                <Modal.Header closeButton>
                    <Modal.Title>Terrarium hinzufügen</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control required  onChange={(e) => handleChangeName(e.target.value) } type="text" />
                                <Form.Control.Feedback type="invalid">Bit
                                    te einen Namen für das Terrarium angeben</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="terrariumHeight">
                                <Form.Label>Höhe (in cm)</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeHeight(Number(e.target.value)) } type="text" />
                                <Form.Control.Feedback type="invalid">Bitte trage die Höhe des Terrarium in cm ein</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumWidth">
                                <Form.Label>Breite (in cm)</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeWidth(Number(e.target.value)) } type="text" />
                                <Form.Control.Feedback type="invalid">Bitte trage die Breite des Terrarium in cm ein</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="terrariumDepth">
                                <Form.Label>Tiefe (in cm)</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeDepth(Number(e.target.value)) } type="text" />
                                <Form.Control.Feedback type="invalid">Bitte trage die Höhe des Terrarium in cm ein</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                    </Row>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" type="submit">
                        Abspeichern
                    </Button>
                    <Button variant="secondary" onClick={() => handleClose()}>
                        Abbrechen
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    )
}