package com.example.springbootmicroservice3.service;

import com.example.springbootmicroservice3.model.Role;
import com.example.springbootmicroservice3.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService
{


    Optional<User> findByUsername(String username);

    List<User> getAllUsers();

    void changeRole(Role newRole, String username);

     Optional<User> getUserById(Long userId);

    List<User> getUsersByRole(Role role);
    User updateUser(Long id, User updatedUser);


    void addFeedback(Long userId, String userFeedback);
}
