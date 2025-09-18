import Title from "../components/Title.tsx";
import type {Species} from "../types/Species.ts";
import SpeciesCard from "../components/species/SpeciesCard.tsx";
import {Col, Row} from "react-bootstrap";
import {useState} from "react";
import SpeciesModalAdd from "../components/species/SpeciesModalAdd.tsx";
import type {SpeciesCreation} from "../types/SpeciesCreation.ts";

type SpeciesPageProps = {
    speciesList: Species[],
    addSpecies: (species: SpeciesCreation) => void,
    updateSpecies: (species: Species) => void,
    deleteSpecies: (species: Species) => void
}

export default function SpeciesPage({speciesList, addSpecies, updateSpecies, deleteSpecies}: SpeciesPageProps) {
    const [show, setShow] = useState<boolean>(false)

    return (
        <>
            <Title
                title="Spezies"
                page="species"
                setShow={setShow}/>
            <SpeciesModalAdd
                show={show}
                setShow={setShow}
                addSpecies={addSpecies}
            />
            <Row style={{"alignItems": "baseline"}}>
                {speciesList.length === 0
                    ? <p>Keine Tiere eingetragen</p>
                    :
                    speciesList.map(species => <Col xs={2}>
                        <SpeciesCard species={species}
                                     deleteSpecies={deleteSpecies}
                                     updateSpecies={updateSpecies}
                        /></Col>)
                }
            </Row>
        </>

    )
}