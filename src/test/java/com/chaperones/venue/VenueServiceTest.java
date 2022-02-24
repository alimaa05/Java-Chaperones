package com.chaperones.venue;

import com.chaperones.activity.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
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
        Venue venue3 = new Venue(3, "Same name ", "area3", "address3");
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
    void recogniseSQLFailsForAddVenueTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Venue venue2 = new Venue(2, "name2", "area2", "address2");
        List<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2));

        // When
        Venue venue3 = new Venue(3, "name3", "area3", "address3");
        when(DAO.getAll()).thenReturn(venues);
        when(DAO.add(any())).thenReturn(0); //SQL BREAKS
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            underTest.addNewVenue(venue3);
        });

        // Then
        verify(DAO, times(1)).add(venue3);
        assertEquals("Venue could not be added", thrown.getMessage());
    }

    @Test
    void getAllVenues() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Venue venue2 = new Venue(2, "name2", "area2", "address2");
        List<Venue> venues = new ArrayList<>(Arrays.asList(venue1, venue2));

        // When
        when(DAO.getAll()).thenReturn(venues);
        List<Venue> actual = underTest.getAllVenues();

        // Then
        assertThat(actual).isEqualTo(venues);
    }

    @Test
    void getAvailableActivitiesAtVenueTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");

        Activity activity1 = new Activity(1, 1, 1, "activity1", "desc1", LocalDate.of(2022, 5, 5), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        Activity activity2 = new Activity(2, 4, 1, "activity2", "desc2", LocalDate.of(2022, 5, 6), LocalTime.of(12,30,0), "1hr", 5.5, 5, true);
        Activity activity3 = new Activity(3, 2, 2, "activity3", "desc3", LocalDate.of(2022, 5, 7), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        List<Activity> activities = new ArrayList<>(Arrays.asList(activity1, activity2, activity3));
        List<Activity> selected = new ArrayList<>();

        Integer id = 1;
        boolean cancelled = false;

        // When
        when(DAO.getById(id)).thenReturn(venue1);
        for (Activity a : activities) {
            if (Objects.equals(a.getVenue_id(), id) && a.getCancelled() == cancelled) {
                selected.add(a);
            }
        }
        when(DAO.getActivities(id, cancelled)).thenReturn(selected);
        List<Activity> actual = underTest.getActivitiesAtVenue(id, cancelled);

        // Then
        List<Activity> expected = new ArrayList<>(List.of(activity1));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getCancelledActivitiesAtVenueTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");

        Activity activity1 = new Activity(1, 1, 1, "activity1", "desc1", LocalDate.of(2022, 5, 5), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        Activity activity2 = new Activity(2, 4, 1, "activity2", "desc2", LocalDate.of(2022, 5, 6), LocalTime.of(12,30,0), "1hr", 5.5, 5, true);
        Activity activity3 = new Activity(3, 2, 2, "activity3", "desc3", LocalDate.of(2022, 5, 7), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        List<Activity> activities = new ArrayList<>(Arrays.asList(activity1, activity2, activity3));
        List<Activity> selected = new ArrayList<>();

        Integer id = 1;
        boolean cancelled = true;

        // When
        when(DAO.getById(id)).thenReturn(venue1);
        for (Activity a : activities) {
            if (Objects.equals(a.getVenue_id(), id) && a.getCancelled() == cancelled) {
                selected.add(a);
            }
        }
        when(DAO.getActivities(id, cancelled)).thenReturn(selected);
        List<Activity> actual = underTest.getActivitiesAtVenue(id, cancelled);

        // Then
        List<Activity> expected = new ArrayList<>(List.of(activity2));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void cantGetActivitiesAtUnknownVenueTest() {
        // Given
        Activity activity1 = new Activity(1, 1, 1, "activity1", "desc1", LocalDate.of(2022, 5, 5), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        Activity activity2 = new Activity(2, 4, 1, "activity2", "desc2", LocalDate.of(2022, 5, 6), LocalTime.of(12,30,0), "1hr", 5.5, 5, true);
        Activity activity3 = new Activity(3, 2, 2, "activity3", "desc3", LocalDate.of(2022, 5, 7), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        List<Activity> activities = new ArrayList<>(Arrays.asList(activity1, activity2, activity3));
        List<Activity> selected = new ArrayList<>();

        Integer id = 3;
        boolean cancelled = false;

        // When
        when(DAO.getById(id)).thenReturn(null);
        for (Activity a : activities) {
            if (Objects.equals(a.getVenue_id(), id) && a.getCancelled() == cancelled) {
                selected.add(a);
            }
        }
        when(DAO.getActivities(id, cancelled)).thenReturn(selected);

        VenueNotFoundException thrown = assertThrows(VenueNotFoundException.class, () -> {
            underTest.getActivitiesAtVenue(id, cancelled);
        });

        // Then
        verify(DAO, never()).getActivities(id, cancelled);
        assertEquals("Venue with id " + id + " could not be found", thrown.getMessage());
    }

    @Test
    void wrongIdStillReturnsEmptyVenueListTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");

        Activity activity1 = new Activity(1, 1, 1, "activity1", "desc1", LocalDate.of(2022, 5, 5), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        Activity activity2 = new Activity(2, 4, 1, "activity2", "desc2", LocalDate.of(2022, 5, 6), LocalTime.of(12,30,0), "1hr", 5.5, 5, true);
        Activity activity3 = new Activity(3, 2, 2, "activity3", "desc3", LocalDate.of(2022, 5, 7), LocalTime.of(12,30,0), "1hr", 5.5, 5, false);
        List<Activity> activities = new ArrayList<>(Arrays.asList(activity1, activity2, activity3));
        List<Activity> selected = new ArrayList<>();

        Integer id = 3;
        boolean cancelled = false;

        // When
        when(DAO.getById(id)).thenReturn(venue1); //this fails to throw
        for (Activity a : activities) {
            if (Objects.equals(a.getVenue_id(), id) && a.getCancelled() == cancelled) {
                selected.add(a);
            }
        }
        when(DAO.getActivities(id, cancelled)).thenReturn(selected);

        List<Activity> actual = underTest.getActivitiesAtVenue(id, cancelled);

        // Then
        List<Activity> expected = new ArrayList<>();
        verify(DAO, times(1)).getActivities(id, cancelled);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getVenueByIdTest() {
        Venue venue1 = new Venue(1, "name1", "area1", "address1");

        // When
        when(DAO.getById(1)).thenReturn(venue1);
        Venue actual = underTest.getVenueById(1);

        // Then
        assertThat(actual).isEqualTo(venue1);
    }

    @Test
    void cantGetUnknownVenueByIdTest() {
        // Given
        Integer id = 3;

        // When
        when(DAO.getById(id)).thenReturn(null);
        VenueNotFoundException thrown = assertThrows(VenueNotFoundException.class, () -> {
            underTest.getVenueById(3);
        });

        // Then
        verify(DAO, times(1)).getById(id);
        assertEquals("Venue with id " + id + " could not be found", thrown.getMessage());
    }

    @Test
    void updateVenueByIdTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Integer id = 1;

        // When
        Venue venue3 = new Venue(3, "name3", "area3", "address3");
        when(DAO.getById(id)).thenReturn(venue1);
        when(DAO.updateById(any(), any())).thenReturn(1);
        underTest.updateVenueById(id, venue3);

        // Then
        verify(DAO, times(1)).getById(id);
        verify(DAO, times(1)).updateById(id, venue3);
    }

    @Test
    void cantUpdateUnknownVenueByIdTest() {
        //Given
        Integer id = 5;

        // When
        Venue venue3 = new Venue(3, "name3", "area3", "address3");
        when(DAO.getById(id)).thenReturn(null);
        when(DAO.updateById(any(), any())).thenReturn(1);
        VenueNotFoundException thrown = assertThrows(VenueNotFoundException.class, () -> {
            underTest.updateVenueById(id, venue3);
        });

        // Then
        verify(DAO, times(1)).getById(id);
        verify(DAO, never()).updateById(id, venue3);
        assertEquals("Venue with id " + id + " could not be found", thrown.getMessage());
    }

    @Test
    void recogniseSQLFailsForUpdateVenueByIdTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Venue venue3 = new Venue(3, "name3", "area3", "address3");
        Integer id = 1;

        // When
        when(DAO.getById(id)).thenReturn(venue1);
        when(DAO.updateById(any(), any())).thenReturn(0); //SQL BREAKS
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            underTest.updateVenueById(id, venue3);
        });

        // Then
        verify(DAO, times(1)).getById(id);
        verify(DAO, times(1)).updateById(id, venue3);
        assertEquals("Venue with id " + id + " could not be updated", thrown.getMessage());
    }

    @Test
    void deleteVenueByIdTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Integer id = 1;

        // When
        when(DAO.getById(id)).thenReturn(venue1);
        when(DAO.deleteById(any())).thenReturn(1);
        underTest.deleteVenueById(id);

        // Then
        verify(DAO, times(1)).getById(id);
        verify(DAO, times(1)).deleteById(id);
    }

    @Test
    void cantDeleteUnknownVenueByIdTest() {
        // Given
        Integer id = 3;

        // When
        when(DAO.getById(id)).thenReturn(null);
        when(DAO.deleteById(any())).thenReturn(1);
        VenueNotFoundException thrown = assertThrows(VenueNotFoundException.class, () -> {
            underTest.deleteVenueById(id);
        });

        // Then
        verify(DAO, times(1)).getById(id);
        verify(DAO, never()).deleteById(id);
        assertEquals("Venue with id " + id + " could not be found", thrown.getMessage());
    }

    @Test
    void recogniseSQLFailsForUpdateDeleteByIdTest() {
        // Given
        Venue venue1 = new Venue(1, "name1", "area1", "address1");
        Integer id = 1;

        // When
        when(DAO.getById(id)).thenReturn(venue1);
        when(DAO.updateById(any(), any())).thenReturn(0); //SQL BREAKS
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            underTest.deleteVenueById(id);
        });

        // Then
        verify(DAO, times(1)).getById(id);
        verify(DAO, times(1)).deleteById(id);
        assertEquals("Venue with id " + id + " could not be deleted", thrown.getMessage());
    }
}