import Title from "../components/Title.tsx";
import {useState} from "react";
import type {Terrarium} from "../types/Terrarium.ts";
import type {TerrariumCreation} from "../types/TerrariumCreation.ts";
import {Col, Row} from "react-bootstrap";
import TerrariumCard from "../components/terraria/TerrariumCard.tsx";
import TerrariumModalAdd from "../components/terraria/TerrariumModalAdd.tsx";

type TerrariumPageProps = {
    terrariumList: Terrarium[],
    addTerrarium: (terrarium: TerrariumCreation) => void,
    updateTerrarium: (terrarium: Terrarium) => void,
    deleteTerrarium: (terrarium: Terrarium) => void
}

export default function TerrariumPage ({terrariumList, addTerrarium, updateTerrarium, deleteTerrarium}: TerrariumPageProps) {
    const [show, setShow] = useState<boolean>(false)


    return (
        <>
            <Title
                title="Terrarium"
                page="terraria"
                setShow={setShow}/>
            <TerrariumModalAdd
                show={show}
                setShow={setShow}
                addTerrarium={addTerrarium}
            />
            <Row style={{"alignItems": "baseline"}}>
                {terrariumList.length === 0
                    ? <p>Keine Terrarien eingetragen</p>
                    :
                    terrariumList.map(terrarium => <Col xs={2}>
                        <TerrariumCard terrarium={terrarium}
                                     deleteTerrarium={deleteTerrarium}
                                     updateTerrarium={updateTerrarium}
                        /></Col>)
                }
            </Row>
        </>
    )
}