import Title from "../components/Title.tsx";
import type {Animal} from "../types/Animal.ts";
import type {Species} from "../types/Species.ts";
import AnimalCard from "../components/animals/AnimalCard.tsx";
import {Col, Row} from "react-bootstrap";
import {useState} from "react";
import AnimalAddForm from "../components/animals/AnimalAddForm.tsx";

type AnimalPageProps = {
    animalList: Animal[],
    getAnimals: () => void,
    addAnimal: (animal: Animal) => void,
    speciesList: Species[],
    getSpecies: () => void
}

export default function AnimalPage({animalList, addAnimal, speciesList}: AnimalPageProps) {
    const [show, setShow] = useState<boolean>(false)


    return (
        <>
            <Title
            title="Deine Lieblinge"
            page="animals"
            setShow={setShow}/>
            <AnimalAddForm
                show={show}
                setShow={setShow}
                speciesList={speciesList}
                addAnimal={addAnimal}
            />
            <Row>
                {animalList.length === 0
                    ? <p>Keine Tiere eingetragen</p>
                    :
                    animalList.map(animal => <Col xs={2}><AnimalCard animal={animal}/></Col>)
                }
            </Row>

        </>

    )
}