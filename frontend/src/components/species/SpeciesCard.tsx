import {Button, Card} from "react-bootstrap";
import type {Species} from "../../types/Species.ts";
import SpeciesModalDelete from "./SpeciesModalDelete.tsx";
import {useState} from "react";

type SpeciesCardProps = {
    species: Species,
    deleteSpecies: (species: Species) => void
}

export default function SpeciesCard ({species, deleteSpecies}: SpeciesCardProps) {
    const [showDelete, setShowDelete] = useState(false)
    return (
        <Card>
            <Card.Body>
                <Card.Title>{species.genus}</Card.Title>
                <Card.Img className="speciesImage" variant="top" src={species.imgUrl}/>
            </Card.Body>
            <Card.Footer>
                <Button onClick={() => setShowDelete(true)}>Löschen</Button>
                <SpeciesModalDelete species={species}
                                    deleteSpecies={deleteSpecies}
                                    show={showDelete}
                                    setShow={setShowDelete}
                />
            </Card.Footer>
        </Card>
    )
}