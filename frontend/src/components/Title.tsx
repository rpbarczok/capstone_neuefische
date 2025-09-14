import {Col, Row} from 'react-bootstrap'
import {useContext } from 'react'
import {ThemeContext} from "../contexts/ThemeContext.ts";
import {PlusCircle} from "react-bootstrap-icons";


type TitleProps = {
    title: string,
    page: string,
    setShow: (show: boolean) => void
}

export default function Title ( { title, page, setShow } : TitleProps) {

    const theme = useContext(ThemeContext)

    return (
        <Row className={'heading ' + page + '-' + theme }>
            <Col>
                <h5>
                    {title}
                </h5>
            </Col>
            <Col>
                {page!=="start"?<PlusCircle size={24} className="float-end" onClick={()=>setShow(true)}/>:""}
            </Col>
        </Row>
    )
}