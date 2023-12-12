package com.example.springbootmicroservice3.controller;

import com.example.springbootmicroservice3.model.Role;
import com.example.springbootmicroservice3.model.User;
import com.example.springbootmicroservice3.repository.UserRepository;
import com.example.springbootmicroservice3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/byRole/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Role role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
   @PutMapping("change/{role}")//api/user/change/{role}
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal User user, @PathVariable Role role)
    {
        userService.changeRole(role, user.getUsername());

        return ResponseEntity.ok(true);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User updatedUserResult = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(updatedUserResult);
    }

    @PostMapping("/signup") //api/user/signup
    public ResponseEntity<String> signUp(@RequestBody User userr) {

        // Map UserDto to User entity
        User user = User.builder()
                .name(userr.getName())
                .username(userr.getUsername())
                .password(userr.getPassword())
                .phone(userr.getPhone())
                .adresse(userr.getAdresse())
                .image(userr.getImage())
                .role(userr.getRole())
                .statut(userr.getStatut())
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
    public void addFeedback(@PathVariable Long userId, @RequestBody String userFeedback) {
        userService.addFeedback(userId, userFeedback);
    }

}
