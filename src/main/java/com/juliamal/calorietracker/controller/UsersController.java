package com.juliamal.calorietracker.controller;

import com.juliamal.calorietracker.dto.response.UserResponse;
import com.juliamal.calorietracker.mappers.UserMapper;
import com.juliamal.calorietracker.model.Users;
import com.juliamal.calorietracker.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userMapper.toDtoList(usersService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody Users user) {
        return ResponseEntity.ok(userMapper.toDto(usersService.addUser(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toDto(usersService.getUserById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody Users user) {
        return ResponseEntity.ok(userMapper.toDto(usersService.updateUser(id, user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
