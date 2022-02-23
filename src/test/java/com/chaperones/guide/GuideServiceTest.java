package com.chaperones.guide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

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
        Guide testGuide = new Guide(1, "Colin", "01424 346816", "colin@gmail.com");

        // When
           when(mockDAO.add(testGuide)).thenReturn(1);
           underTest.addGuide(testGuide);
        // Then
        verify(mockDAO, times(1)).add(testGuide);
    }

    @Test
    void allGuides() {
        // Given

        // When

        // Then
    }

    @Test
    void guideById() {
        // Given

        // When

        // Then
    }

    @Test
    void updateGuide() {
        // Given

        // When

        // Then
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