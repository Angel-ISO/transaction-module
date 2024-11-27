package org.jala.university.infrastructure.services;

import org.mockito.Mock;

import org.jala.university.domain.entities.User;
import org.jala.university.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNameUser_ShouldReturnDistinctUsernames() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setUsername("user2");

        User user3 = new User();
        user3.setId(UUID.randomUUID());
        user3.setUsername("user1");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3));

        List<String> result = userService.getAllNameUser();

        assertEquals(2, result.size());
        assertTrue(result.contains("user1"));
        assertTrue(result.contains("user2"));
    }

    @Test
    void testGetAllNameUser_ShouldReturnEmptyList_WhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(List.of());


        List<String> result = userService.getAllNameUser();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserByUsername_ShouldReturnUser_WhenUserExists() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("user1");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        User result = userService.getUserByUsername("user1");

        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    void testGetUserByUsername_ShouldReturnNull_WhenUserDoesNotExist() {
        when(userRepository.findAll()).thenReturn(List.of());

        User result = userService.getUserByUsername("nonexistentUser");

        assertNull(result);
    }
}