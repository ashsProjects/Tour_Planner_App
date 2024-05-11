import React from 'react';
import { describe, expect, test, jest, beforeEach } from '@jest/globals';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import SaveFile from '@components/Header/SaveFile'; // Adjust the import path as necessary

describe('SaveFile', () => {
    const mockToggleSaveFile = jest.fn();
    const props = {
        showSaveFile: true,
        toggleSaveFile: mockToggleSaveFile,
        tripName: 'Sample Trip',
        places: [
            { name: 'Place 1', latitude: '123', longitude: '456' },
            { name: 'Place 2', latitude: '789', longitude: '012' }
        ]
    };

    beforeEach(() => {
        // Mock for URL.createObjectURL
        global.URL.createObjectURL = jest.fn();
        
        render(<SaveFile {...props} />);
    });

    test('renders Save as JSON option', () => {
        screen.getByText('Save as JSON');
    });

    test('renders Save as KML option', () => {
        screen.getByText('Save as KML');
    });

    test('calls SaveTrip function with JSON format on clicking Save as JSON', () => {
        const jsonButton = screen.getByText('Save as JSON');
        userEvent.click(jsonButton);
        expect(mockToggleSaveFile).toHaveBeenCalled();
    });

    test('calls SaveTripKML function with KML format on clicking Save as KML', () => {
        const kmlButton = screen.getByText('Save as KML');
        userEvent.click(kmlButton);
        expect(URL.createObjectURL).toHaveBeenCalled();
        expect(mockToggleSaveFile).toHaveBeenCalled();
    });

    test('closes the modal on clicking Cancel', () => {
        const cancelButton = screen.getByText('Cancel');
        userEvent.click(cancelButton);
        expect(mockToggleSaveFile).toHaveBeenCalled();
    });
});
