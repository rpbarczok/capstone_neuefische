import type {Species} from "../../types/Species.ts";
import {type FormEvent, useState} from "react";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";

type SpeciesModalUpdateProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    updateSpecies: (species: Species) => void,
    species: Species
}

export default function SpeciesModalUpdate({show, setShow, updateSpecies, species}: SpeciesModalUpdateProps) {

    const originalSpecies = species
    const [updatedSpecies, setUpdatedSpecies] = useState(species)
    const [validated, setValidated] = useState<boolean>(false)

    function handleChangeGenus(value: string) {
        setUpdatedSpecies(
            {
                ...updatedSpecies,
                genus: value
            }
        )
    }

    function handleChangeOrigin(value: string) {
        setUpdatedSpecies(
            {
                ...updatedSpecies,
                origin: value
            }
        )
    }

    function handleChangeImgUrl(value: string) {
        setUpdatedSpecies(
            {
                ...updatedSpecies,
                imgUrl: value
            }
        )
    }

    function undo() {
        setUpdatedSpecies(originalSpecies)
    }

    function submitUpdatedSpecies(e: FormEvent, form: HTMLFormElement) {
        e.preventDefault()
        if (!form.checkValidity) {
            setValidated(true)
        } else {
            updateSpecies(updatedSpecies)
            setValidated(false)
            setShow(false)
        }
    }

    function handleClose() {
        setValidated(false)
        setUpdatedSpecies(originalSpecies)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={()=>handleClose()}>
            <Form noValidate validated={validated} onSubmit={(e) => submitUpdatedSpecies(e, e.currentTarget)}>
                <Modal.Header>
                    <Modal.Title>Spezies verändern</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col>
                            <Form.Group controlId="speciesGenus">
                                <Form.Label>Genus</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeGenus(e.target.value)} type="text" value={updatedSpecies.genus}>
                                </Form.Control>
                                <Form.Control.Feedback type="invalid">Bitte gib den Namen der Species an</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="speciesOrigin">
                                <Form.Label>Herkunft</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeOrigin(e.target.value)} type="text" value={updatedSpecies.origin}>
                                </Form.Control>
                                <Form.Control.Feedback type="invalid">Bitte gib die Herkunftsregion der Species an</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="speciesImgUrl">
                                <Form.Label>Bild-URL</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeImgUrl(e.target.value)} type="text" value={updatedSpecies.imgUrl}>
                                </Form.Control>
                                <Form.Control.Feedback type="invalid">Bitte gib einen Link zu einem Bild an</Form.Control.Feedback>
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