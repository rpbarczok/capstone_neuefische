import {Col, Container, Row} from "react-bootstrap";
import useNotes from "./hooks/useNotes.ts";
import useAnimals from "./hooks/useAnimals.ts";
import useSpecies from "./hooks/useSpecies.ts";
import Navigation from "./components/Navigation.tsx";
import DataLoading from "./components/DataLoading.tsx";
import Notifier from "./components/Notifier.tsx";
import Footer from "./components/Footer.tsx";
import type {Theme} from "./utils/toggleTheme.ts";
import AppRouter from "./pages/AppRouter.tsx";

type BasicStructureProps = {
    theme: Theme,
    setTheme :(theme: Theme)  => void
    isLoading: boolean
}

export default function BasicStructure({theme, setTheme, isLoading}: BasicStructureProps) {

    const [notes, addNote, removeNote] = useNotes()
    const [animalList, getAnimals, addAnimal] = useAnimals(addNote)
    const [speciesList, getSpecies, addSpecies] = useSpecies(addNote)

    return (
        <Container fluid className='d-flex flex-column vh-100'>
            <Row>
                <Navigation theme={theme}
                            setTheme={setTheme}/>
            </Row>
            {isLoading ? <DataLoading /> : ''}
            <Notifier notes={notes} removeNote={removeNote}/>
            <Row className="flex-grow-1">
                <Col>
                    <AppRouter
                        animalList={animalList}
                        getAnimals={getAnimals}
                        addAnimal={addAnimal}
                        speciesList={speciesList}
                        getSpecies={getSpecies}
                        addSpecies={addSpecies}
                    />
                </Col>
            </Row>
            <Row className="bg-body-secondary">
                <Footer />
            </Row>
        </Container>
        )

}
