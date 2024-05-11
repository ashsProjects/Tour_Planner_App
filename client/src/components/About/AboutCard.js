import React from 'react';
import { Card, CardBody, CardImg, CardSubtitle, CardText, CardTitle, Col } from 'reactstrap';

export default function AboutCard(props) {
    const cardProps = props.team
        ? { xs: 12 }
        : { xs: 12, sm: 12, md: 10, lg: 8, xl: 8 };

    return (
        <Col className="mb-4 mx-auto" {...cardProps}>
            <Card>
                <CardImg top width="100%" src={props.pic} />
                <CardBody>
                    <CardTitle tag="h5">{props.title}</CardTitle>
                    <CardSubtitle tag="h6" className="mb-2 text-muted">{props.subTitle}</CardSubtitle>
                    <CardText style={{maxHeight:'175px', overflowY:'auto'}}>{props.text}</CardText>
                </CardBody>
            </Card>
        </Col>
    );
}