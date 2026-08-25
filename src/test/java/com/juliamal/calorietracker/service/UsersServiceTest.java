package com.juliamal.calorietracker.service;

import com.juliamal.calorietracker.dto.request.UserRequest;
import com.juliamal.calorietracker.exception.ResourceNotFoundException;
import com.juliamal.calorietracker.model.Users;
import com.juliamal.calorietracker.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    private Users existingUser;

    @BeforeEach
    void setUp() {
        existingUser = new Users();
        existingUser.setId(1L);
        existingUser.setUsername("julia");
        existingUser.setEmail("julia@example.com");
        existingUser.setPassword("hashed-old-password");
        existingUser.setAge(25);
        existingUser.setWeight(60.0);
        existingUser.setHeight(170.0);
        existingUser.setDailyCalorieGoal(2000);
        existingUser.setDailyProteinGoal(120.0);
        existingUser.setDailyCarbsGoal(250.0);
        existingUser.setDailyFatGoal(70.0);
    }

    @Test
    void getUserById_whenExists_returnsUser() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Users result = usersService.getUserById(1L);
        assertThat(result.getUsername()).isEqualTo("julia");
    }

    @Test
    void getUserById_whenNotFound_throwsResourceNotFoundException() {
        when(usersRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usersService.getUserById(404L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("404");
    }

    @Test
    void addUser_hashesPasswordBeforeSaving() {
        UserRequest request = new UserRequest(
                "newUser", "new@example.com", "plainPassword123",
                30, 70.0, 180.0, 2200, 130.0, 260.0, 75.0);

        when(passwordEncoder.encode("plainPassword123")).thenReturn("hashed:plainPassword123");
        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Users result = usersService.addUser(request);
        assertThat(result.getPassword()).isEqualTo("hashed:plainPassword123");
        assertThat(result.getUsername()).isEqualTo("newUser");
        verify(passwordEncoder, times(1)).encode("plainPassword123");
    }

    @Test
    void updateUser_whenPasswordBlank_keepsOldPassword() {
        UserRequest request = new UserRequest(
                "julia", "julia@example.com", "",
                26, 61.0, 170.0, 2100, 125.0, 255.0, 72.0);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Users result = usersService.updateUser(1L, request);
        assertThat(result.getPassword()).isEqualTo("hashed-old-password");
        assertThat(result.getAge()).isEqualTo(26);
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void updateUser_whenPasswordProvided_reHashesPassword() {
        UserRequest request = new UserRequest(
                "julia", "julia@example.com", "nowyPassword123",
                26, 61.0, 170.0, 2100, 125.0, 255.0, 72.0);

        when(usersRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("nowyPassword123")).thenReturn("hashed:nowyPassword123");
        when(usersRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Users result = usersService.updateUser(1L, request);
        assertThat(result.getPassword()).isEqualTo("hashed:nowyPassword123");
    }

    @Test
    void deleteUser_whenNotFound_throwsAndNeverDeletes() {
        when(usersRepository.existsById(404L)).thenReturn(false);
        assertThatThrownBy(() -> usersService.deleteUser(404L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(usersRepository, never()).deleteById(any());
    }
}