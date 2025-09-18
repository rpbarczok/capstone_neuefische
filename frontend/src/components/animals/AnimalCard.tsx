import type {Animal} from "../../types/Animal.ts";
import {Button, Card, CardFooter} from "react-bootstrap";
import millipede from '../../assets/millipede512.png';
import {useState} from "react";
import AnimalModalDelete from "./AnimalModalDelete.tsx";

type AnimalCardProps = {
    animal: Animal,
    deleteAnimal: (animal: Animal) => void
}
export default function AnimalCard ({animal, deleteAnimal}: AnimalCardProps) {

    const [showDelete, setShowDelete] = useState(false)

    return (
        <Card>
            <Card.Img className="animalCard" variant="top" src={millipede}/>
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
                <AnimalModalDelete
                    animal={animal}
                    show={showDelete}
                    setShow={setShowDelete}
                    deleteAnimal={deleteAnimal} />
            </CardFooter>

        </Card>
    )
}