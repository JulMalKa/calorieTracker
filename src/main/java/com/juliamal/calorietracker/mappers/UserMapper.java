package com.juliamal.calorietracker.mappers;

import com.juliamal.calorietracker.dto.response.UserResponse;
import com.juliamal.calorietracker.model.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(Users user);
    List<UserResponse> toDtoList(List<Users> users);
}
