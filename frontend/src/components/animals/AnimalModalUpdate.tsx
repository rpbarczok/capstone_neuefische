import {useState} from "react";
import {Button, Col, Form, Modal, Row} from "react-bootstrap";
import type {Animal} from "../../types/Animal.ts";
import type {Species} from "../../types/Species.ts";

type AnimalModalUpdateProps = {
    show: boolean,
    setShow: (show: boolean) => void,
    updateAnimal: (animal: Animal) => void,
    animal: Animal,
    speciesList: Species[]
}

export default function AnimalModalUpdate({show, setShow, updateAnimal, animal, speciesList}: AnimalModalUpdateProps) {

    const originalAnimal = animal
    const [updatedAnimal, setUpdatedAnimal] = useState(animal)

    function handleChangeName(value: string) {
        setUpdatedAnimal(
            {
                ...updatedAnimal,
                name: value
            }
        )
    }

    function handleChangeSpecies(value: string) {
        setUpdatedAnimal(
            {
                ...updatedAnimal,
                species: value
            }
        )
    }

    function handleChangeImgUrl(value: string) {
        setUpdatedAnimal(
            {
                ...updatedAnimal,
                imgUrl: value
            }
        )
    }

    function handleChangeBirthdate(value: string) {
        setUpdatedAnimal(
            {
                ...updatedAnimal,
                birthDate: value
            }
        )
    }

    function handleChangeGender(value: string) {
        setUpdatedAnimal(
            {
                ...updatedAnimal,
                gender: value
            }
        )
    }

    function undo() {
        setUpdatedAnimal(originalAnimal)
    }

    function submitUpdatedAnimal() {
        updateAnimal(updatedAnimal)
        setShow(false)
    }

    return (
        <Modal show={show} onHide={()=> setShow(false)}>
            <Modal.Header>
                <Modal.Title>Tier verändern</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalName">
                                <Form.Label>Name</Form.Label>
                                <Form.Control
                                    onChange={(e) => handleChangeName(e.target.value) }
                                    type="text"
                                    value={updatedAnimal.name}/>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="animalSpecies">
                                <Form.Label>Species</Form.Label>
                                <Form.Select onChange={(e) => handleChangeSpecies(e.target.value)} aria-label="Auswahl der Spezies">
                                    {speciesList.map(species => <option selected={updatedAnimal.species === species.genus} value={species.genus}>{species.genus}</option>)}
                                </Form.Select>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalGender">
                                <Form.Label>Species</Form.Label>
                                <Form.Select onChange={(e) => handleChangeGender(e.target.value)} aria-label="Auswahl des Geschlechts">
                                    <option selected={updatedAnimal.gender==="weiblich"} value="weiblich">weiblich</option>
                                    <option value="männlich" selected={updatedAnimal.gender==="männlich"}>männlich</option>
                                    <option value="zweigeschlechtlich" selected={updatedAnimal.gender==="zweigeschlechtlich"}>zweigeschlechtlich</option>
                                    <option value="unbekannt" selected={updatedAnimal.gender==="unbekannt"}>unbekannt</option>
                                </Form.Select>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group controlId="animalBirthdate">
                                <Form.Label>Geburtsdatum</Form.Label>
                                <Form.Control
                                    onChange={(e) => handleChangeBirthdate(e.target.value) }
                                    value={updatedAnimal.birthDate}
                                    type="date" />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="animalImgUrl">
                                <Form.Label>Image-URL</Form.Label>
                                <Form.Control
                                    onChange={(e) => handleChangeImgUrl(e.target.value) }
                                    type="text"
                                    value={updatedAnimal.imgUrl}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="primary" onClick={() => submitUpdatedAnimal()}>
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