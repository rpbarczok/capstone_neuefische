import {Button, ButtonGroup, Card} from "react-bootstrap";
import type {Species} from "../../types/Species.ts";
import SpeciesModalDelete from "./SpeciesModalDelete.tsx";
import {useState} from "react";
import SpeciesModalUpdate from "./SpeciesModalUpdate.tsx";

type SpeciesCardProps = {
    species: Species,
    updateSpecies: (species: Species) => void,
    deleteSpecies: (species: Species) => void
}

export default function SpeciesCard ({species, updateSpecies, deleteSpecies}: SpeciesCardProps) {
    const [showDelete, setShowDelete] = useState(false)
    const [showUpdate, setShowUpdate] = useState(false)
    return (
        <Card>
            <Card.Body>
                <Card.Title>{species.genus}</Card.Title>
                <Card.Img className="speciesImage" variant="top" src={species.imgUrl}/>
                <Card.Text>Herkunft: {species.origin}</Card.Text>
            </Card.Body>
            <Card.Footer>
                <ButtonGroup>
                    <Button onClick={() => setShowUpdate(true)}>Update</Button>
                    <Button onClick={() => setShowDelete(true)}>Löschen</Button>
                </ButtonGroup>
                <SpeciesModalDelete species={species}
                                    deleteSpecies={deleteSpecies}
                                    show={showDelete}
                                    setShow={setShowDelete}
                />
                <SpeciesModalUpdate species={species}
                                    updateSpecies={updateSpecies}
                                    show={showUpdate}
                                    setShow={setShowUpdate}
                />
            </Card.Footer>
        </Card>
    )
}