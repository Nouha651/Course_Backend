package com.example.springbootmicroservice3.auth;




import com.example.springbootmicroservice3.model.Role;
import com.example.springbootmicroservice3.repository.UserRepository;
import com.example.springbootmicroservice3.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.springbootmicroservice3.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(String username, String password, String name) {
        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .name(name)
                .createTime(LocalDateTime.now())
                .build();

        repository.save(user); // Move this line here

        // Generate JWT token and return the authentication response
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userRole(Role.USER)
                .id(user.getId())
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userRole(user.getRole())
                .id(user.getId())


                //.name(user.getName())
                .build();


    }


}