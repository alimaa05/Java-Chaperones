package com.chaperones.guide;

import com.chaperones.activity.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuideServiceTest {
//    @InjectMocks
//    GuideService underTest;
//
//    @Mock
//    GuideDAO mockdao;
//
//    @BeforeEach
//    public void init() {
//        MockitoAnnotations.openMocks(underTest);
//
//    }
    private GuideService underTest;
    private GuideDAO mockDAO;

    @BeforeEach
    void setUp() {
        mockDAO = Mockito.mock(GuideDAO.class);
        underTest = new GuideService(mockDAO);
    }
        @Test
    void addGuide() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));

        // When
            when(mockDAO.getAll()).thenReturn(testList);
            when(mockDAO.add(any())).thenReturn(1);
           underTest.addGuide(testGuide);
        // Then
        verify(mockDAO, times(1)).add(testGuide);
    }
    @Test
    void addGuideWhenPhoneNumberAndEmailIsSameAsExistingGuides() {
        // Given
        List<Guide> testList = new ArrayList<>();
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        testList.add(testGuide);
        Guide addedGuide = new Guide(2, "la", "01424 346816", "blah@gmail.com");

        // When
        //doThrow(IllegalStateException.class).when(mockDAO).add(addedGuide);
        //when(mockDAO.add(addedGuide)).thenThrow(IllegalArgumentException.class);
        when(mockDAO.getAll()).thenReturn(testList);
        when(mockDAO.add(any())).thenReturn(1);
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> {
            underTest.addGuide(addedGuide);
        });
        // Then
        assertEquals("Guide already exists", ex.getMessage());
        verify(mockDAO, never()).add(addedGuide);

    }

    @Test
    void returnAllGuides() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide testGuide1 = new Guide(2, "la", "01424 346889", "la@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide, testGuide1));

        // When
        when(mockDAO.getAll()).thenReturn(testList);
        underTest.allGuides();
        // Then
        verify(mockDAO,times(1)).getAll();

    }
    @Test
    void returnAllGuidesWhenNull() {
        // Given

        List<Guide> testList = new ArrayList<>(Arrays.asList());

        // When
        when(mockDAO.getAll()).thenReturn(testList);
        underTest.allGuides();
        // Then
        verify(mockDAO,times(1)).getAll();

    }

    @Test
    void getGuideById() {

        // Given
        Guide expected = new Guide(1, "blah", "01424 346816", "blah@gmail.com");

        // When
        when(mockDAO.getById(1)).thenReturn(expected);
        Guide actual = underTest.guideById(1);


        // Then
        verify(mockDAO, times(1)).getById(1);
        assertThat(actual).isEqualTo(expected);

        //OR

        //Given
//        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
//        Guide testGuide1 = new Guide(2, "la", "01424 346889", "la@gmail.com");
//        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide, testGuide1));

        // When
//        when(mockDAO.getAll()).thenReturn(testList);
//        when(mockDAO.getById(anyInt())).thenReturn(testGuide);
//        underTest.guideById(1);

        // Then
//        verify(mockDAO,times(1)).getById(1);
    }

    @Test
    void getGuideByIdWhenIdDoesNotExist() {

        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
//        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));
        // When
        when(mockDAO.getById(2)).thenReturn(null);
        GuideDoesNotExistException ex = assertThrows(GuideDoesNotExistException.class, () -> {
            underTest.guideById(2);
        });
        // Then
        assertEquals("This guide does not exist", ex.getMessage());
        verify(mockDAO, times(1)).getById(2);

    }

    @Test
    void updateGuideById() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide changedTestGuide = new Guide(1, "blah", "01424 346889", "blah@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));

        // When
        when(mockDAO.getById(1)).thenReturn(testGuide);
        when(mockDAO.updateById(1,changedTestGuide)).thenReturn(1);
        int actual = underTest.updateGuide(1, changedTestGuide);
        // Then
        verify(mockDAO,times(1)).updateById(1, changedTestGuide);
        assertThat(actual).isEqualTo(1);
    }
    @Test
    void updateGuideByIdWhenIdDoesNotExist() {
        // Given
        Guide testGuide = new Guide(1, "blah", "01424 346816", "blah@gmail.com");
        Guide changedTestGuide = new Guide(3, "blah", "01424 346889", "blah@gmail.com");
        List<Guide> testList = new ArrayList<>(Arrays.asList(testGuide));

        // When
        when(mockDAO.getById(3)).thenReturn(null);
        when(mockDAO.updateById(3,changedTestGuide)).thenReturn(0);
        GuideDoesNotExistException ex = assertThrows(GuideDoesNotExistException.class, () -> {
            underTest.updateGuide(3, changedTestGuide);
        });

        // Then
        assertEquals("This guide does not exist", ex.getMessage());
        verify(mockDAO, times(1)).getById(3);
        verify(mockDAO,never()).updateById(3, changedTestGuide);

    }

    @Test
    void deleteGuide() {
        // Given

        // When

        // Then
    }

    @Test
    void guidesActivities() {
        // Given

        // When

        // Then
    }
}