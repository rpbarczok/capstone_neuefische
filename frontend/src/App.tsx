import Router from "./pages/Router"
import Navigation from "./components/Navigation.tsx";
import Footer from "./components/Footer.tsx";
import {Col, Container, Row} from "react-bootstrap";
import {useState} from "react";
import { ThemeContext } from './contexts/ThemeContext.ts';
import {LoadingContext} from "./contexts/LoadingContext.ts";

function App() {

    const [theme, setTheme] = useState<'light' | 'dark'>(window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light')
    const [isLoading, setIsLoading] = useState(false)
    return (
      <ThemeContext.Provider value={theme}>
          <LoadingContext.Provider value={{ isLoading: isLoading, setIsLoading: setIsLoading }}>
          <Container fluid className='d-flex flex-column vh-100'>
              <Row>
                  <Navigation theme={theme}
                              setTheme={setTheme}/>
              </Row>
              <Row className="flex-grow-1">
                  <Col>
                      <Router/>
                  </Col>
              </Row>
              <Row className="bg-body-secondary">
                  <Footer />
              </Row>
          </Container>
          </LoadingContext.Provider>
      </ThemeContext.Provider>
    )
}

export default App
