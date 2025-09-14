import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import type {Animal} from "../../types/Animal.ts";
import type {Species} from "../../types/Species.ts";

type AnimalAddFormProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    addAnimal: (animal: Animal) => void
    speciesList: Species[]
}

export default function AnimalAddForm({show, setShow, speciesList}: AnimalAddFormProps) {

    return (
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>Tier hinzufügen</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control type="text" />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="animalSpecies">
                                <Form.Label>Species</Form.Label>
                                <Form.Select aria-label="Auswahl der Spezies">
                                    <option>Wähle eine Species</option>
                                    {speciesList.map(species => <option value={species.id}>{species.genus}</option>)}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalGender">
                                <Form.Label>Species</Form.Label>
                                <Form.Select aria-label="Auswahl des Geschlechts">
                                    <option>Wähle ein Geschlecht</option>
                                    <option value="weiblich">weiblich</option>
                                    <option value="männlich">männlich</option>
                                    <option value="zweigeschlechtlich">zweigeschlechtlich</option>
                                    <option value="unbekannt">unbekannt</option>
                                </Form.Select>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="animalBirthdate">
                                <Form.Label>Geburtsdatum</Form.Label>
                                <Form.Control type="date" />
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setShow(false)}>
                    Abbrechen
                </Button>
                <Button variant="primary" onClick={() => setShow(false)}>
                    Abspeichern
                </Button>
            </Modal.Footer>
        </Modal>
    )
}