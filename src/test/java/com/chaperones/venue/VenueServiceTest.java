package com.chaperones.venue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenueServiceTest {

    private VenueService underTest;
    private VenueDAO venueDAO;

    @BeforeEach
    void init() {
        venueDAO = mock(VenueDAO.class);
        underTest = new VenueService(venueDAO);
    }

    @Test
    void addNewVenue() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Venue venue2 = new Venue(2, "name2", "area2", "address2");
        List<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2));

        // When
        Venue venue3 = new Venue(3, "name3", "area3", "address3");
        when(venueDAO.add(venue3)).thenReturn(1);
        underTest.addNewVenue(venue3);

        // Then
        verify(venueDAO, times(1)).add(venue3);
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