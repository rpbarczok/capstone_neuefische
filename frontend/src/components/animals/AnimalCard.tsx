import type {Animal} from "../../types/Animal.ts";
import {Card} from "react-bootstrap";
import millipede from '../../assets/millipede512.png';

type AnimalCardProps = {
    animal: Animal
}
export default function AnimalCard ({animal}: AnimalCardProps) {
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
        </Card>
    )
}