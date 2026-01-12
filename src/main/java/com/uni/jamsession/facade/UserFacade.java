package com.uni.jamsession.facade;

import com.uni.jamsession.dtos.user.UserDto;
import com.uni.jamsession.dtos.user.UserEditDto;
import com.uni.jamsession.dtos.user.UserRegisterDto;
import com.uni.jamsession.mappers.InstrumentAndRatingMapper;
import com.uni.jamsession.mappers.UserMapper;
import com.uni.jamsession.model.User;
import com.uni.jamsession.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final InstrumentAndRatingMapper instrumentAndRatingMapper;

    public List<UserDto> getAllUsers(){
        return userService.getAllUsers().stream().map(
                userMapper::toDto
        ).collect(Collectors.toList());
    }

    public UserDto registerUser(UserRegisterDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userService.registerUser(user, userDto.password());
        return userMapper.toDto(savedUser);
    }
    public UserDto getUserById(int id) {
        return userMapper.toDto(userService.getUserById(id));
    }

    public UserDto editUser(int id, UserEditDto userEditDto) {
        User user = userService.getUserById(id);
        userMapper.updateUserFromDto(userEditDto, user);
        userService.updateGenres(user, userEditDto.favouriteGenreIds());
        User savedUser = userService.saveUser(user);
        return userMapper.toDto(savedUser);
    }
}
