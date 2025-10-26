import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import type {Species} from "../../types/Species.ts";
import {type FormEvent, useState} from "react";
import type {AnimalCreation} from "../../types/AnimalCreation.ts";
import type {Terrarium} from "../../types/Terrarium.ts";

type AnimalAddFormProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    addAnimal: (animal: AnimalCreation) => void
    speciesList: Species[]
    terrariumList: Terrarium[]
}

export default function AnimalModalAdd({show, setShow, speciesList, addAnimal, terrariumList}: AnimalAddFormProps) {
    const emptyAnimalCreation={name: "", birthDate: "", species: "", gender: "", imgUrl: "", terrarium: ""}
    const [newAnimal, setNewAnimal] = useState<AnimalCreation>(emptyAnimalCreation)
    const [validated, setValidated] = useState<boolean>(false)

    function handleChangeName(value: string) {

        setNewAnimal(
            {
                ...newAnimal,
                name: value
            }
        )
    }

    function handleChangeSpecies(value: string) {

        if (value !== "default")
        setNewAnimal(
            {
                ...newAnimal,
                species: value
            }
        )
    }

    function handleChangeTerrarium(value: string) {

        setNewAnimal(
            {
                ...newAnimal,
                terrarium: value
            }
        )
    }

    function handleChangeGender(value: string) {

        setNewAnimal(
            {
                ...newAnimal,
                gender: value
            }
        )
    }

    function handleChangeBirthdate(value: string) {

        setNewAnimal(
            {
                ...newAnimal,
                birthDate: value
            }
        )
    }

    function handleChangeImgUrl (value: string) {

        setNewAnimal(
            {
                ...newAnimal,
                imgUrl: value
            }
        )
    }

    function submitNewAnimal (e: FormEvent<HTMLFormElement>, form: HTMLFormElement) {
        e.preventDefault()
        if (!form.checkValidity()) {
            setValidated(true)
        } else {
            addAnimal(newAnimal)
            setNewAnimal(emptyAnimalCreation)
            setValidated(false)
            setShow(false)
        }
    }

    function handleClose() {
        setValidated(false)
        setNewAnimal(emptyAnimalCreation)
        setShow(false)
    }
    
    return (
        <Modal show={show} onHide={() => handleClose()}>
            <Form noValidate validated={validated} onSubmit={(e) => submitNewAnimal(e, e.currentTarget)}>
                <Modal.Header closeButton>
                    <Modal.Title>Tier hinzufügen</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control required onChange={(e) => handleChangeName(e.target.value) } type="text" />
                                <Form.Control.Feedback type='invalid'>Bitte einen Namen für das Tier eingeben</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="animalSpecies">
                                <Form.Label>Species</Form.Label>
                                <Form.Select required onChange={(e) => handleChangeSpecies(e.target.value)} aria-label="Auswahl der Spezies">
                                    <option value="">Spezies auswählen</option>
                                    {speciesList.map(species => <option value={species.genus}>{species.genus}</option>)}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalTerrarium">
                                <Form.Label>Terrarium</Form.Label>
                                <Form.Select required onChange={(e)=> handleChangeTerrarium(e.target.value)} aria-label="Auswahl des Terrariums">
                                    <option value="">Terrarium auswählen</option>
                                    {terrariumList.map(terrarium => <option value={terrarium.name}>{terrarium.name}</option>)}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalGender">
                                <Form.Label>Species</Form.Label>
                                <Form.Select
                                    required
                                    onChange={(e) => handleChangeGender(e.target.value)} aria-label="Auswahl des Geschlechts">
                                    <option value="">Geschlecht auswählen</option>
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
                                <Form.Control required onChange={(e) => handleChangeBirthdate(e.target.value) } type="date" />
                                <Form.Control.Feedback type='invalid'>Bitte das Geburtsdatum oder Ankunftsdatum eintragen</Form.Control.Feedback>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalImgUrl">
                                <Form.Label>Url für ein Image</Form.Label>
                                <Form.Control onChange={(e) => handleChangeImgUrl(e.target.value) } type="text" />
                            </Form.Group>
                        </Col>
                    </Row>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => handleClose()}>
                        Abbrechen
                    </Button>
                    <Button variant="primary" type='submit'>
                        Abspeichern
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    )
}