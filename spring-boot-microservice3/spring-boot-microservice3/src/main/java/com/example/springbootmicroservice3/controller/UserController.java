package com.example.springbootmicroservice3.controller;

import com.example.springbootmicroservice3.model.Role;
import com.example.springbootmicroservice3.model.User;
import com.example.springbootmicroservice3.repository.UserRepository;
import com.example.springbootmicroservice3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user") //pre-path
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        // Check for null roles before returning
        users.forEach(user -> {
            if (user.getRole() == null) {
                user.setRole(Role.USER); // Set a default role if null
            }
        });

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/byRole/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Role role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
   @PutMapping("change/{role}")//api/user/change/{role}
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal User user, @PathVariable Role role)
    {
        userService.changeRole(role, user.getUsername());

        return ResponseEntity.ok(true);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User updatedUserResult = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(updatedUserResult);
    }

    @PostMapping("/signup") //api/user/signup
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> signUp(@RequestBody User userr) {

        // Map UserDto to User entity
        User user = User.builder()
                .name(userr.getName())
                .username(userr.getUsername())
                .password(passwordEncoder.encode(userr.getPassword()))
                .phone(userr.getPhone())
                .adresse(userr.getAdresse())
                .image(userr.getImage())
                .role(userr.getRole())
                .statut(userr.getStatut())
                .feedback(userr.getFeedback())
                .build();

        // Save the user
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
    @PostMapping("/logout") //api/user/logout
    public ResponseEntity<String> logout() {
        try {
            // Log out the currently authenticated user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }

    @PostMapping("/{userId}/feedback") //api/user/{userId}/feedback
    @PreAuthorize("hasRole('USER')")
    public void addFeedback(@PathVariable Long userId, @RequestBody String userFeedback) {
        userService.addFeedback(userId, userFeedback);
    }
    @GetMapping("/{userId}/feedback")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getFeedback(@PathVariable Long userId) {
        String feedback = userService.getFeedbackByUserId(userId);

        if (feedback != null) {
            return ResponseEntity.ok(feedback);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hello") //api/user/hello
    public String hello() {
        return "Hello from Spring boot & Keycloak";
    }
}
