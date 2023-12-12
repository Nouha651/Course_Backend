package com.example.springbootmicroservice3.repository;

import com.example.springbootmicroservice3.model.Role;
import com.example.springbootmicroservice3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    //findBy+fieldName

    Optional<User> findByUsername(String username);

    List<User> findAll();

    @Modifying
    @Query("update User set role = :role where username = :username")
    void updateUserRole(@Param("username") String username, @Param("role") Role role);

    List<User> findByRole(Role role);
}
