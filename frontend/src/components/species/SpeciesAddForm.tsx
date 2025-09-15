import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import {useState} from "react";
import type {SpeciesCreation} from "../../types/SpeciesCreation.ts";


type SpeciesAddFormProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    addSpecies: (species: SpeciesCreation) => void
}

export default function SpeciesAddForm({show, setShow, addSpecies}: SpeciesAddFormProps) {
    const emptySpeciesCreation={genus: "", imgUrl: ""}
    const [newSpecies, setNewSpecies] = useState<SpeciesCreation>(emptySpeciesCreation)

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

    function submitNewSpecies () {
        addSpecies(newSpecies)
        setNewSpecies(emptySpeciesCreation)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Species hinzufügen</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Row>
                        <Col>
                            <Form.Group controlId="speciesGenus">
                                <Form.Label>Genus</Form.Label>
                                <Form.Control onChange={(e) => handleChangeGenus(e.target.value) } type="text" />
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
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setShow(false)}>
                    Abbrechen
                </Button>
                <Button variant="primary" onClick={() => submitNewSpecies()}>
                    Abspeichern
                </Button>
            </Modal.Footer>
        </Modal>
    )
}