import React from 'react';
import { Container, Row, } from 'reactstrap';
import { memberData } from "./teamInfo";
import AboutCard from "./AboutCard";

export default function About() {
    return (
        <Container>
            <MemberCards />
        </Container>
    );
}

function MemberCards() {
    return (
        <Row>
            {memberData.map(member =>
                <AboutCard
                    key={member.name}
                    title={member.name}
                    text={member.bio}
                    subTitle={member.homeTown}
                    pic={member.imagePath}
                />
            )}
        </Row>
    );
}