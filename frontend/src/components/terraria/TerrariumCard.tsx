import type {Terrarium} from "../../types/Terrarium.ts";
import {useState} from "react";
import {Button, ButtonGroup, Card} from "react-bootstrap";
import TerrariumModalDelete from "./TerrariumModalDelete.tsx";
import TerrariumModalUpdate from "./TerrariumModalUpdate.tsx";
import terrariumPlaceholder from "../../assets/terrarium.png";

type TerrariumCardProps = {
    terrarium: Terrarium,
    updateTerrarium: (terrarium: Terrarium) => void,
    deleteTerrarium: (terrarium: Terrarium) => void
}

export default function SpeciesCard ({terrarium, updateTerrarium, deleteTerrarium}: TerrariumCardProps) {
    const [showDelete, setShowDelete] = useState(false)
    const [showUpdate, setShowUpdate] = useState(false)
    return (
        <Card>
            <Card.Body>
                <Card.Title>{terrarium.name}</Card.Title>
                <Card.Img className="terrariumImage" variant="top" src={terrariumPlaceholder}/>
                <Card.Text>
                    Höhe {terrarium.height} cm <br/>
                    Breite {terrarium.width} cm <br/>
                    Tiefe {terrarium.depth} cm <br/>
                    Volumen {terrarium.volume} l</Card.Text>
            </Card.Body>
            <Card.Footer>
                <ButtonGroup>
                    <Button onClick={() => setShowUpdate(true)}>Update</Button>
                    <Button onClick={() => setShowDelete(true)}>Löschen</Button>
                </ButtonGroup>
                <TerrariumModalDelete terrarium={terrarium}
                                    deleteTerrarium={deleteTerrarium}
                                    show={showDelete}
                                    setShow={setShowDelete}
                />
                <TerrariumModalUpdate terrarium={terrarium}
                                    updateTerrarium={updateTerrarium}
                                    show={showUpdate}
                                    setShow={setShowUpdate}
                />
            </Card.Footer>
        </Card>
    )
}