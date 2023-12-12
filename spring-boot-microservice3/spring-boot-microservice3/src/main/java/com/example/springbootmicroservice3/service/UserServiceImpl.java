package com.example.springbootmicroservice3.service;

import com.example.springbootmicroservice3.model.Role;
import com.example.springbootmicroservice3.model.User;
import com.example.springbootmicroservice3.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Override
    public User saveUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        //user.setCreateTime(LocalDateTime.now());

        return userRepository.save(user);
    }*/

    @Override
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    @Transactional //Transactional is required when executing an update/delete query.
    public void changeRole(Role newRole, String username)
    {
        userRepository.updateUserRole(username, newRole);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        // Update fields as needed
        existingUser.setName(updatedUser.getName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAdresse(updatedUser.getAdresse());
        //existingUser.setImage(updatedUser.getImage());

        // If the password is provided, encode and update it
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword(), passwordEncoder);
        }

        // Save the updated user
        return userRepository.save(existingUser);
    }

    @Override
    public void addFeedback(Long userId, String userFeedback) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Add feedback to the user
        user.addFeedback(userFeedback);

        // Save the updated user to the database
        userRepository.save(user);
    }


}
