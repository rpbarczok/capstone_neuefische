import millipede from "../assets/millipede32.png"
import {Button, Nav, Image} from "react-bootstrap";
import {MoonStarsFill, SunFill} from "react-bootstrap-icons";
import {useNavigate} from "react-router-dom";
import {type Theme, toggleTheme} from "../utils/toggleTheme.ts";

type NavigationProps = {
    theme: Theme,
    setTheme : (theme: Theme) => void
}

export default function Navigation ({theme, setTheme}: NavigationProps) {
    const nav = useNavigate()

    return (
        <Nav className="bg-body-secondary" variant="tabs" defaultActiveKey="/animals">
            <Nav.Item>
                <Nav.Link >
                    <Image src={millipede} alt="cute millipede" onClick={() => nav("/")} />
                </Nav.Link>
            </Nav.Item>
            <Nav.Item>
                <Nav.Link onClick={() => nav("/animals")}>Lieblinge</Nav.Link>
            </Nav.Item>
            <Nav.Item >
                <Nav.Link onClick={() => nav("/terraria")}>Wohnungen</Nav.Link>
            </Nav.Item>
            <Nav.Item >
                <Nav.Link onClick={() => nav("/species")}>Spezies</Nav.Link>
            </Nav.Item>
            <Nav.Item className="ms-auto">
                <Button className="theme-button" onClick={() => toggleTheme(theme, setTheme)}>
                    {theme === "dark" ? <MoonStarsFill/> : <SunFill/>}
                </Button>
            </Nav.Item>
        </Nav>
    )
}