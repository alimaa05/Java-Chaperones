package com.chaperones.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserSQLTest {

    private UserSQL testing;
    private JdbcTemplate mock;

    @BeforeEach
    void init() {
        mock = mock(JdbcTemplate.class);
        testing = new UserSQL(mock);
    }

    @Test
    void addUserTest() {
        // Given
        User testUser = new User(1, "Adib", "08878 238789", "adib@hotmail.com");

        // When
        testing.add(testUser);

        // Then
        Mockito.verify(mock, times(1)).update("INSERT INTO users (name, phoneNumber, email) VALUES(?, ?, ?)", "Adib", "08878 238789", "adib@hotmail.com");
    }
}
