package com.chaperones.venue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenueSQLTest {

    private VenueSQL underTest;
    private JdbcTemplate mock;

    @BeforeEach
    void init() {
        mock = mock(JdbcTemplate.class);
        underTest = new VenueSQL(mock);
    }

    @Test
    void addTest() {
        // Given
        Venue testVenue = new Venue(1, "London Dungeons", "Southbank", "The Queen's Walk, London SE1 7PB");
        // When
        underTest.add(testVenue);
        // Then
        Mockito.verify(mock, times(1)).update("INSERT INTO venues (name, area, address) VALUES(?, ?, ?)","London Dungeons","Southbank","The Queen's Walk, London SE1 7PB");
    }

    @Test
    void getAllTest() {
        // Given
        List<Venue> mockList = new ArrayList<Venue>();
        mockList.add(new Venue(1, "London Dungeons", "Southbank", "The Queen's Walk, London SE1 7PB"));
        mockList.add(new Venue(2, "London Eye", "River Thames", "Riverside Building, County Hall, London SE1 7PB"));
        mockList.add(new Venue(3, "Sea Life London", "River Thames", "Riverside Building, County Hall, Westminster Bridge Rd London SE1 7PB"));
        // When
        List<Venue> actual = underTest.getAll();
        // Then
    }

    @Test
    void getByIdTest() {
        // Given

        // When

        // Then
    }

    @Test
    void updateByIdTest() {
        // Given

        // When

        // Then
    }

    @Test
    void deleteByIdTest() {
        // Given

        // When

        // Then
    }
}