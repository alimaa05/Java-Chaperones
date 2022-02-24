package com.chaperones.user;

import com.chaperones.activity.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService underTest;
    private UserDAO mockDAO;

    @BeforeEach
    void setup() {
        mockDAO = mock(UserDAO.class);
        underTest = new UserService((mockDAO));
    }

    @Test
    void addNewUser() {
        // Given
        List<User> testUsers = new ArrayList<>();
        User testUser = new User(1, "ash", "08933 283409", "ash@gmail.com");
        User testUser1 = new User(2, "misty", "03833 283409", "misty@gmail.com");

        // When
        when(mockDAO.getAll()).thenReturn(testUsers);
        when(mockDAO.add(any())).thenReturn(1);
        underTest.addNewUser(testUser);

        // Then
        verify(mockDAO, times(1)).getAll();
        verify(mockDAO, times(1)).add(testUser);
    }

    @Test
    void cantAddDuplicateUserTest() {
        // Given
        User testUser = new User(1, "ash", "08933 283409", "ash@gmail.com");
        User testUser1 = new User(2, "misty", "03833 283409", "misty@gmail.com");
        User testUser2 = new User(3, "ash", "34933 283409", "ahdkj@gmail.com");
        List<User> testUsers = new ArrayList<>(Arrays.asList(testUser, testUser1));

        // When
        when(mockDAO.getAll()).thenReturn(testUsers);
        when(mockDAO.add(any())).thenReturn(1);
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            underTest.addNewUser(testUser1);
        });

        // Then
        verify(mockDAO, never()).add(testUser1);
        assertEquals("User already exists", thrown.getMessage());
    }

    @Test
    void getAllUsers() {
        // Given
        List<User> testUsers = new ArrayList<>();
        User testUser = new User(1, "ash", "08933 283409", "ash@gmail.com");
        User testUser1 = new User(2, "misty", "03833 283409", "misty@gmail.com");

        // When
        testUsers.add(testUser);
        when(mockDAO.getAll()).thenReturn(testUsers);
        underTest.getAllUsers();

        // Then
        verify(mockDAO, times(1)).getAll();
    }

    @Test
    void returnAllUsersWhenEmpty() {
        // Given
        List<User> testUsers = new ArrayList<>();

        // When
        when(mockDAO.getAll()).thenReturn(testUsers);
        underTest.getAllUsers();

        // Then
        verify(mockDAO, times(1)).getAll();
    }

    @Test
    void getUserById() {
        // Given
        User expectedUser = new User(1, "ash", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(1)).thenReturn(expectedUser);
        User actual = underTest.getUserById(1);

        // Then
        verify(mockDAO, times(1)).getById(1);
        assertThat(actual).isEqualTo(expectedUser);
    }

    @Test
    void cantGetUserByIdIfUserExists() {
        // Given
        User expectedUser = new User(2, "ash", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(1)).thenReturn(null);
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            underTest.getUserById(1);
        });

        // Then
        verify(mockDAO, times(1)).getById(1);
        assertEquals("User could not be found", thrown.getMessage());
    }

    @Test
    void updateUserById() {
        // Given
        User originalUser = new User(2, "ash", "08933 283409", "ash@gmail.com");
        User updatedUser = new User(2, "misty", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(2)).thenReturn(originalUser);
        when(mockDAO.updateById(2,updatedUser)).thenReturn(1);
        underTest.updateUserById(2, updatedUser);

        // Then
        verify(mockDAO, times(1)).updateById(2,updatedUser);
    }

    @Test
    void updateUserByIdWhenIdDoesNotExist() {
        // Given
        User originalUser = new User(2, "ash", "08933 283409", "ash@gmail.com");
        User updatedUser = new User(2, "misty", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(3)).thenReturn(null);
        when(mockDAO.updateById(3,updatedUser)).thenReturn(0);
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            underTest.updateUserById(3, updatedUser);
        });

        // Then
        verify(mockDAO, times(1)).getById(3);
        verify(mockDAO, never()).updateById(3,updatedUser);
        assertEquals("User with id 3 could not be found", thrown.getMessage());
    }

    @Test
    void updateUserByIdWhenIdDoesNotEqualOne() {
        // Given
        User originalUser = new User(2, "ash", "08933 283409", "ash@gmail.com");
        User updatedUser = new User(2, "misty", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(2)).thenReturn(originalUser);
        when(mockDAO.updateById(2,updatedUser)).thenReturn(0);
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            underTest.updateUserById(2, updatedUser);
        });

        // Then
        verify(mockDAO, times(1)).getById(2);
        verify(mockDAO, times(1)).updateById(2,updatedUser);
        assertEquals("User with id 2 could not be updated", thrown.getMessage());
    }

    @Test
    void deleteUserById() {
        // Given
        User originalUser = new User(2, "ash", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(2)).thenReturn(originalUser);
        when(mockDAO.deleteById(2)).thenReturn(1);
        underTest.deleteUserById(2);

        // Then
        verify(mockDAO, times(1)).getById(2);
        verify(mockDAO, times(1)).deleteById(2);
    }

    @Test
    void deleteUserByIdWhenIdDoesNotExist() {
        // Given
        User deletedUser = new User(2, "misty", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(3)).thenReturn(null);
        when(mockDAO.updateById(3,deletedUser)).thenReturn(0);
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            underTest.deleteUserById(3);
        });

        // Then
        verify(mockDAO, times(1)).getById(3);
        verify(mockDAO, never()).deleteById(3);
        assertEquals("User with id 3 could not be found", thrown.getMessage());
    }

    @Test
    void deleteUserByIdWhenIdDoesNotEqualOne() {
        // Given
        User originalUser = new User(1, "ash", "08933 283409", "ash@gmail.com");
        User updatedUser = new User(2, "misty", "08933 283409", "ash@gmail.com");

        // When
        when(mockDAO.getById(2)).thenReturn(originalUser);
        when(mockDAO.deleteById(2)).thenReturn(0);
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            underTest.deleteUserById(2);
        });

        // Then
        verify(mockDAO, times(1)).getById(2);
        verify(mockDAO, times(1)).deleteById(2);
        assertEquals("User with id 2 could not be deleted", thrown.getMessage());
    }

    @Test
    void getActivitiesByUser() {
        // Given
        User originalUser = new User(1, "ash", "08933 283409", "ash@gmail.com");
        List<Activity> testActivity = new ArrayList<>();
        // When
        when(mockDAO.getById(1)).thenReturn(originalUser);
        when(mockDAO.getActivities(1, false)).thenReturn(testActivity);
            underTest.getActivitiesByUser(1, false);

        // Then
        verify(mockDAO, times(1)).getById(1);
        verify(mockDAO, times(1)).getActivities(1, false);
    }

    @Test
    void addUserToActivity() {
        // Given


        // When


        // Then


    }

    @Test
    void removeUserFromActivity() {
        // Given


        // When


        // Then


    }
}