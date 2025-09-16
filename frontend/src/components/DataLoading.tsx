import {useEffect, useState} from "react";
import {Col, Modal, Row, Spinner} from "react-bootstrap";

export default function DataLoading() {
    const [showLoading, setShowLoading] = useState(false)

    useEffect(() => {
        const timer = setInterval(() => setShowLoading(true), 500)
        return () => clearInterval(timer)
    }, [])

    if (!showLoading) return <></>

    return <Modal backdropClassName='transparent-backdrop' backdrop show centered animation={false}>
        <Modal.Body>
            <Row>
                <Col className='d-flex justify-content-center'>
                    <Spinner animation='border' role='status'>
                        <span className='visually-hidden'>Loading...</span>
                    </Spinner>
                </Col>
            </Row>
            <Row >
                <Col className='d-flex justify-content-center'>
                    Test-Version. Laden kann bis 30 Sekunden dauern...
                </Col>
            </Row>
        </Modal.Body>
    </Modal>
}