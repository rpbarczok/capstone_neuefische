import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import {type FormEvent, useState} from "react";
import type {SpeciesCreation} from "../../types/SpeciesCreation.ts";


type SpeciesAddFormProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    addSpecies: (species: SpeciesCreation) => void
}

export default function SpeciesModalAdd({show, setShow, addSpecies}: SpeciesAddFormProps) {
    const emptySpeciesCreation={genus: "", imgUrl: "", origin: ""}
    const [newSpecies, setNewSpecies] = useState<SpeciesCreation>(emptySpeciesCreation)
    const [validated, setValidated] = useState<boolean>(false)
    function handleChangeGenus(value: string) {

        setNewSpecies(
            {
                ...newSpecies,
                genus: value
            }
        )
    }

    function handleChangeImgUrl(value: string) {

        setNewSpecies(
            {
                ...newSpecies,
                imgUrl: value
            }
        )
    }

    function handleChangeOrigin(value: string) {

        setNewSpecies(
            {
                ...newSpecies,
                origin: value
            }
        )
    }

    function handleClose() {
        setValidated(false)
        setNewSpecies(emptySpeciesCreation)
        setShow(false)
    }
    function submitNewSpecies (e: FormEvent<HTMLFormElement>, form: HTMLFormElement) {
        e.preventDefault()
        if (!form.checkValidity()) {
            setValidated(true)
        } else {
            addSpecies(newSpecies)
            setNewSpecies(emptySpeciesCreation)
            setValidated(false)
            setShow(false)
        }
    }

    return (
        <Modal show={show} onHide={() => handleClose()}>
            <Form noValidate validated={validated} onSubmit={(e) => submitNewSpecies(e, e.currentTarget)}>
                <Modal.Header closeButton>
                    <Modal.Title>Spezies hinzufügen</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col>
                            <Form.Group controlId="speciesGenus">
                                <Form.Label>Genus</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeGenus(e.target.value) } type="text" />
                                <Form.Control.Feedback type='invalid'>Bitte gib den Namen der Species an</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="speciesOrigin">
                                <Form.Label>Herkunft</Form.Label>
                                <Form.Control onChange={(e) => handleChangeOrigin(e.target.value) } type="text" />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="speciesImg">
                                <Form.Label>Image Url</Form.Label>
                                <Form.Control onChange={(e) => handleChangeImgUrl(e.target.value) } type="text" />
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