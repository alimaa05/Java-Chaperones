package com.chaperones.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest {
private ActivityService underTest;
private ActivityDAO mockDAO;

// before each test is run it will carry out this section
@BeforeEach
    void setup(){
    // create mock of ActivityDAO only want to test the service in isolation so we create a mock(fake) version of the ActivityDAO
    mockDAO = mock(ActivityDAO.class);
    // creating new variable so we can call on 'underTest' within out tests
    underTest = new ActivityService(mockDAO);
}

    @Test
    void canGetAllActivities() {
        // Given

        // want to return a list
        List<Activity> testingGetAllActivities = new ArrayList<>();


        // When

        // when you call mockDAO get all in a list and return the list 'testingGetAllActivities'
        when(mockDAO.getAll()).thenReturn(testingGetAllActivities);
        underTest.getAllActivities();

        // Then
        verify(mockDAO,times(1)).getAll();


    }

    @Test
    void canGetAllActivitiesWhenNull() {
        // Given

        // want to return a list
        List<Activity> testingGetAllActivities = new ArrayList<>();
        Activity activity = new Activity(1,2,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);
        testingGetAllActivities.add(activity);

        // When

        // when you call mockDAO get all in a list and return the list 'testingGetAllActivities'
        when(mockDAO.getAll()).thenReturn(testingGetAllActivities);
        underTest.getAllActivities();

        // Then
        verify(mockDAO,times(1)).getAll();


    }


    @Test
    void canAddNewActivity() {
        // Given

        // want to return a list
        List<Activity> testingAddNewActivities = new ArrayList<>();
        Activity activity = new Activity(1,2,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);

        // When

        // when you call mockDAO get all in a list and return the list 'testingGetAllActivities'
        when(mockDAO.getAll()).thenReturn(testingAddNewActivities);
        when(mockDAO.add(any())).thenReturn(1);
        underTest.addNewActivity(activity);


        // Then
        verify(mockDAO,times(1)).getAll();
        verify(mockDAO,times(1)).add(activity);


    }

    @Test
    void cantAddDuplicateActivity() {
        // Given

        // want to return a list
        // activities with the same values
        Activity activity = new Activity(1,2,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);
        Activity activity1 = new Activity(2,2,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);
        // getting a list view of array so that array list can recognise
        List<Activity> testingCantDuplicateActivities = new ArrayList<>(Arrays.asList(activity, activity1));

        // When

        // when you call mockDAO get all in a list and return the list 'testingGetAllActivities'
        when(mockDAO.getAll()).thenReturn(testingCantDuplicateActivities);
        when(mockDAO.add(any())).thenReturn(1);
        // throw an exception
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            // calling variable underTest add another activity - add activity1
            underTest.addNewActivity(activity1);
        });

        // Then
        verify(mockDAO,times(1)).getAll();
        assertEquals("Sorry, this activity already exists. Please try again!", thrown.getMessage());

    }


    @Test
    void canGetActivityById() {

        // Given
        Activity expectedActivity = new Activity(1,2,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);


        // When
        when(mockDAO.getById(1)).thenReturn(expectedActivity);
        Activity actual = underTest.getActivityById(1);


        // Then
        verify(mockDAO, times(1)).getById(1);
        assertThat(actual).isEqualTo(expectedActivity);

    }

    @Test
    void canCheckIfActivityByIdExists() {

        // Given
        Activity expectedActivity = new Activity(2,2,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);
//        List<Activity> CheckIfActivityByIdExists = new ArrayList<>(Arrays.asList(expectedActivity));


        // When
        when(mockDAO.getById(1)).thenReturn(null);
        ActivityDoesNotExistException thrown = assertThrows(ActivityDoesNotExistException.class, () -> {
            // calling variable underTest add another activity - add activity1
            underTest.getActivityById(1);
//            Activity actual = underTest.getActivityById(1);
        });


        // Then
        verify(mockDAO, times(1)).getById(1);
        assertEquals("Sorry, activity with id 1 does not exist", thrown.getMessage());
//        assertThat(actual).isEqualTo(expectedActivity);

    }


    @Test
    void updateActivityById() {
        // Given
        Activity originalActivity = new Activity(2,2,2,"Kew Gardens","test", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);
        Activity updatedActivity = new Activity(2,4,2,"London Bridge","newTest", LocalDate.of(2022,03,12), LocalTime.of(13,0,00),"1hr",40.00, 20, false);


        // When
        when(mockDAO.getById(2)).thenReturn(originalActivity);
        when(mockDAO.updateById(2,updatedActivity)).thenReturn(1);
        underTest.updateActivityById(2, updatedActivity);

        // Then

        verify(mockDAO, times(1)).updateById(2,updatedActivity);


    }

    

    @Test
    void deleteActivityById() {
        // Given

        // When

        // Then
    }

    @Test
    void getAllUsersFromGivenActivity() {
        // Given

        // When

        // Then
    }

    @Test
    void getFreeSpaces() {
        // Given

        // When

        // Then
    }
}