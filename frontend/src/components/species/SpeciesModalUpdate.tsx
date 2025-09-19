import type {Species} from "../../types/Species.ts";
import {useState} from "react";
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

    function submitUpdatedSpecies() {
        updateSpecies(updatedSpecies)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={()=> setShow(false)}>
            <Modal.Header>
                <Modal.Title>Spezies verändern</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Row>
                        <Col>
                            <Form.Group controlId="speciesGenus">
                                <Form.Label>Genus</Form.Label>
                                <Form.Control onChange={(e) => handleChangeGenus(e.target.value)} type="text" value={updatedSpecies.genus}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="speciesOrigin">
                                <Form.Label>Herkung</Form.Label>
                                <Form.Control onChange={(e) => handleChangeOrigin(e.target.value)} type="text" value={updatedSpecies.origin}>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="speciesImgUrl">
                                <Form.Label>Bild-URL</Form.Label>
                                <Form.Control onChange={(e) => handleChangeImgUrl(e.target.value)} type="text" value={updatedSpecies.imgUrl
                                }>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={() => submitUpdatedSpecies()}>
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