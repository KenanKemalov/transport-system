package com.example.transportsystemj8.services;
import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.data.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mockito.ArgumentMatcher;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        UserDetails result = userService.loadUserByUsername("testuser");
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_UserDoesNotExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        userService.loadUserByUsername("testuser");
    }

    @Test
    public void testCheckIfUserExists_UserExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        boolean result = userService.checkIfUserExists(user);
        assertTrue(result);
    }

    @Test
    public void testCheckIfUserExists_UserDoesNotExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        boolean result = userService.checkIfUserExists(user);
        assertFalse(result);
    }

    @Test
    public void testSaveUser() {
        userService.saveUser(user);
        verify(userRepository).save(user);
    }

    @Test
    public void testValidateUser_ValidPassword() {
        String password = "password123";
        boolean result = userService.validateUser(password);
        assertTrue(result);
    }

    @Test
    public void testValidateUser_InvalidPassword() {
        String password = "password";
        boolean result = userService.validateUser(password);
        assertFalse(result);
    }

    @Test
    public void testValidateNewPassword_ValidPasswords() {
        String password = "password123";
        String confirmPassword = "password123";
        boolean result = userService.validateNewPassword(password, confirmPassword);
        assertTrue(result);
    }

    @Test
    public void testValidateNewPassword_InvalidPasswords() {
        String password = "password123";
        String confirmPassword = "password1";
        boolean result = userService.validateNewPassword(password, confirmPassword);
        assertFalse(result);
    }


    @Test
    public void testUpdatePassword() {
        BCryptPasswordEncoder actualPasswordEncoder = new BCryptPasswordEncoder();
        userService.updatePassword(user, "newPassword");
        verify(userRepository).save(user);
        assertTrue(actualPasswordEncoder.matches("newPassword", user.getPassword()));
    }



}

