package com.example.transportsystemj8.data.repository;

import com.example.transportsystemj8.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(user));
        Optional<User> result = userRepositoryMock.findByUsername(username);
        assertEquals(Optional.of(user), result);
    }
}

