import type {Animal} from "../../types/Animal.ts";
import {Button, Card, CardFooter} from "react-bootstrap";
import millipede from '../../assets/millipede512.png';
import {useState} from "react";
import AnimalModalDelete from "./AnimalModalDelete.tsx";
import AnimalModalUpdate from "./AnimalModalUpdate.tsx";
import type {Species} from "../../types/Species.ts";

type AnimalCardProps = {
    animal: Animal,
    updateAnimal: (animal: Animal) => void,
    deleteAnimal: (animal: Animal) => void,
    speciesList: Species[]
}
export default function AnimalCard ({animal, updateAnimal, deleteAnimal, speciesList}: AnimalCardProps) {

    const [showDelete, setShowDelete] = useState(false)
    const [showUpdate, setShowUpdate] = useState(false)

    return (
        <Card>
            <Card.Img className="animalCard" variant="top" src={animal.imgUrl == "" ? millipede : animal.imgUrl}/>
            <Card.Body>
                <Card.Title>{animal.name}</Card.Title>
                <Card.Text>
                    Name: {animal.name} <br/>
                    Spezies: {animal.species} <br/>
                    Geschlecht: {animal.gender} <br/>
                    Geburts- bzw. Ankunftsdatum: {animal.birthDate} (vor {animal.age} Tagen)
                </Card.Text>
            </Card.Body>
            <CardFooter>
                <Button onClick = {() => setShowDelete(true)}>Löschen</Button>
                <Button onClick = {() => setShowUpdate(true)}>Update</Button>
                <AnimalModalDelete
                    animal={animal}
                    show={showDelete}
                    setShow={setShowDelete}
                    deleteAnimal={deleteAnimal} />
                <AnimalModalUpdate
                    animal={animal}
                    show={showUpdate}
                    setShow={setShowUpdate}
                    updateAnimal={updateAnimal}
                    speciesList={speciesList}
                    />
            </CardFooter>

        </Card>
    )
}