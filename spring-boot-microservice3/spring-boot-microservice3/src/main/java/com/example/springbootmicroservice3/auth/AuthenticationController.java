package com.example.springbootmicroservice3.auth;



import com.example.springbootmicroservice3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {
    private final UserRepository repository;
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String name = request.get("name");

        return ResponseEntity.ok(service.register(username, password, name));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));

    }
}
