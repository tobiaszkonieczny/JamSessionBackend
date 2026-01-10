//package com.sap.jamsession;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.sap.jamsession.exceptions.ResourceNotFoundException;
//import com.sap.jamsession.model.dtos.CreateJamSessionDto;
//import com.sap.jamsession.model.dtos.EditUserDto;
//import com.sap.jamsession.model.dtos.RegisterUserDto;
//import com.sap.jamsession.mappers.UserMapper;
//import com.sap.jamsession.model.User;
//import com.sap.jamsession.model.dtos.UserDto;
//import com.sap.jamsession.repositories.UserRepository;
//import com.sap.jamsession.security.JwtDecoder;
//import com.sap.jamsession.security.JwtToPrincipalConverter;
//import com.sap.jamsession.security.UserPrinciple;
//import com.sap.jamsession.services.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    void shouldRegisterUserSuccessfully() {
//
//        RegisterUserDto registerUserDto =  RegisterUserDto.builder()
//                .email("john@example.com")
//                .name("John Doe")
//                .password("password123")
//                .build();
//        User user = new User();
//        user.setEmail(registerUserDto.getEmail());
//
//        when(userRepository.existsByEmail(registerUserDto.getEmail())).thenReturn(false);
//        when(passwordEncoder.encode(registerUserDto.getPassword())).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//
//        User registeredUser = userService.registerUser(registerUserDto);
//
//
//        assertNotNull(registeredUser);
//        assertEquals("john@example.com", registeredUser.getEmail());
//        verify(passwordEncoder, times(1)).encode("password123");
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//}