import {Col, Row} from 'react-bootstrap'
import {useContext } from 'react'
import {ThemeContext} from "../contexts/ThemeContext.ts";


type TitleProps = {
    title: string
    cssClass: string
}

export default function Title ({title, cssClass }: TitleProps) {

    const theme = useContext(ThemeContext)

    return (
        <Row className={'heading ' + cssClass + '-' + theme }>
            <Col>
                <h5>{title}</h5>
            </Col>
        </Row>
    )
}