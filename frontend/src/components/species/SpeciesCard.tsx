import {Card} from "react-bootstrap";
import type {Species} from "../../types/Species.ts";

type SpeciesCardProps = {
    species: Species
}
export default function SpeciesCard ({species}: SpeciesCardProps) {
    return (
        <Card>
            <Card.Img className="speciesImage" variant="top" src={species.imgUrl}/>
            <Card.Body>
                <Card.Title>{species.genus}</Card.Title>
            </Card.Body>
        </Card>
    )
}