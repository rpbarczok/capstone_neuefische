import Title from "../components/Title.tsx";
import type {Animal} from "../types/Animal.ts";
import type {Species} from "../types/Species.ts";
import AnimalCard from "../components/animals/AnimalCard.tsx";
import {Col, Row} from "react-bootstrap";
import {useState} from "react";
import AnimalModalAdd from "../components/animals/AnimalModalAdd.tsx";
import type {AnimalCreation} from "../types/AnimalCreation.ts";
import type {Terrarium} from "../types/Terrarium.ts";

type AnimalPageProps = {
    animalList: Animal[],
    addAnimal: (animal: AnimalCreation) => void,
    speciesList: Species[],
    updateAnimal: (animal: Animal) => void
    deleteAnimal: (animal: Animal) => void
    terrariumList: Terrarium[]
}

export default function AnimalPage({animalList, addAnimal, updateAnimal, deleteAnimal, speciesList, terrariumList}: AnimalPageProps) {
    const [show, setShow] = useState<boolean>(false)


    return (
        <>
            <Title
            title="Deine Lieblinge"
            page="animals"
            setShow={setShow}/>
            <AnimalModalAdd
                show={show}
                setShow={setShow}
                speciesList={speciesList}
                addAnimal={addAnimal}
                terrariumList={terrariumList}
            />
            <Row>
                {animalList.length === 0
                    ? <p>Keine Tiere eingetragen</p>
                    :
                    animalList.map(animal => <Col xs={2}><AnimalCard
                        animal={animal}
                        updateAnimal={updateAnimal}
                        deleteAnimal={deleteAnimal}
                        speciesList={speciesList}
                        terrariumList={terrariumList}/></Col>)
                }
            </Row>

        </>

    )
}