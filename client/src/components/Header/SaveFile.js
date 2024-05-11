import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { SaveTrip, SaveTripKML } from '@utils/saveTrip'; 

export default function SaveFile(props) {
    const { tripName, places } = props; 

    function clear() {
        props.toggleSaveFile();
    }

    const footerProps = {
        clear,
        tripName,
        places
    };

    return (
        <Modal isOpen={props.showSaveFile} toggle={clear}>
            <SaveFileHeader toggleSaveFile={clear} />
            <SaveFileBody />
            <SaveFileFooter {...footerProps} />
        </Modal>
    );
}

function SaveFileHeader(props) {
    return (
        <ModalHeader toggle={props.toggleSaveFile}>Save Trip</ModalHeader>
    );
}

function SaveFileBody() {
    return (
        <ModalBody>Choose the format to save your trip:</ModalBody>
    );
}

function SaveFileFooter({ clear, tripName, places }) {
    return (
        <ModalFooter>
            <SaveJSON clear={clear} tripName={tripName} places={places} />
            <SaveKML clear={clear} tripName={tripName} places={places} />
            <CancelButton clear={clear} />
        </ModalFooter>
    );
}

function SaveJSON({ clear, tripName, places }) {
    const handleSaveJSON = () => {
        const fileText = JSON.stringify({ places });
        SaveTrip(tripName, fileText);
        clear(); // Optionally close the modal after saving
    };

    return (
        <Button color="primary" onClick={handleSaveJSON}>
            Save as JSON
        </Button>
    );
}

function SaveKML({ clear, tripName, places }) {
    const handleSaveKML = () => {
        SaveTripKML(tripName, places);
        clear(); // Optionally close the modal after saving
    };

    return (
        <Button color="secondary" onClick={handleSaveKML}>
            Save as KML
        </Button>
    );
}

function CancelButton({ clear }) {
    return (
        <Button color="danger" onClick={clear}>Cancel</Button>
    );
}
