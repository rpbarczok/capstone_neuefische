import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import type {Species} from "../../types/Species.ts";
import {useState} from "react";
import type {AnimalCreation} from "../../types/AnimalCreation.ts";

type AnimalAddFormProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    addAnimal: (animal: AnimalCreation) => void
    speciesList: Species[]
}

export default function AnimalAddForm({show, setShow, speciesList, addAnimal}: AnimalAddFormProps) {
    const emptyAnimalCreation={name: "", birthDate: "", species: "", gender: ""}
    const [newAnimal, setNewAnimal] = useState<AnimalCreation>(emptyAnimalCreation)

    function handleChangeName(value: string) {

        console.log(value)

        setNewAnimal(
            {
                ...newAnimal,
                name: value
            }
        )
    }

    function handleChangeSpecies(value: string) {

        console.log(value)

        setNewAnimal(
            {
                ...newAnimal,
                species: value
            }
        )
    }

    function handleChangeGender(value: string) {

        console.log(value)

        setNewAnimal(
            {
                ...newAnimal,
                gender: value
            }
        )
    }

    function handleChangeBirthdate(value: string) {

        console.log(value)

        setNewAnimal(
            {
                ...newAnimal,
                birthDate: value
            }
        )
    }

    function submitNewAnimal () {
        addAnimal(newAnimal)
        setNewAnimal(emptyAnimalCreation)
        setShow(false)
    }
    
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
                                <Form.Control onChange={(e) => handleChangeName(e.target.value) } type="text" />
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="animalSpecies">
                                <Form.Label>Species</Form.Label>
                                <Form.Select onChange={(e) => handleChangeSpecies(e.target.value)} aria-label="Auswahl der Spezies">
                                    <option>Wähle eine Species</option>
                                    {speciesList.map(species => <option value={species.genus}>{species.genus}</option>)}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalGender">
                                <Form.Label>Species</Form.Label>
                                <Form.Select onChange={(e) => handleChangeGender(e.target.value)} aria-label="Auswahl des Geschlechts">
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
                                <Form.Control onChange={(e) => handleChangeBirthdate(e.target.value) } type="date" />
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setShow(false)}>
                    Abbrechen
                </Button>
                <Button variant="primary" onClick={() => submitNewAnimal()}>
                    Abspeichern
                </Button>
            </Modal.Footer>
        </Modal>
    )
}