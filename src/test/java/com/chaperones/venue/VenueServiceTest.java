package com.chaperones.venue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenueServiceTest {

    private VenueService underTest;
    private VenueDAO DAO;

    @BeforeEach
    void init() {
        DAO = mock(VenueDAO.class);
        underTest = new VenueService(DAO);
    }

    @Test
    void addNewVenueTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Venue venue2 = new Venue(2, "name2", "area2", "address2");
        List<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2));

        // When
        Venue venue3 = new Venue(3, "name3", "area3", "address3");
        when(DAO.getAll()).thenReturn(venues);
        when(DAO.add(any())).thenReturn(1);
        underTest.addNewVenue(venue3);

        // Then
        verify(DAO, times(1)).add(venue3);
    }

    @Test
    void cantAddDuplicateVenueTest() {
        // Given
        Venue venue1 = new Venue(1, "same name", "area1", "address1");
        Venue venue2 = new Venue(2, "name2", "area2", "address2");
        List<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2));

        // When
        Venue venue3 = new Venue(3, "same name", "area3", "address3");
        when(DAO.getAll()).thenReturn(venues);
        when(DAO.add(any())).thenReturn(1);
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            underTest.addNewVenue(venue3);
        });

        // Then
        verify(DAO, never()).add(venue3);
        assertEquals("Venue already exists", thrown.getMessage());
    }

    @Test
    void getAllVenues() {
        // Given

        // When

        // Then
    }

    @Test
    void getActivitiesAtVenue() {
        // Given

        // When

        // Then
    }

    @Test
    void getVenueById() {
        // Given

        // When

        // Then
    }

    @Test
    void updateVenueById() {
        // Given

        // When

        // Then
    }

    @Test
    void deleteVenueById() {
        // Given

        // When

        // Then
    }
}