package com.example.springbootmicroservice3.auth;


import com.example.springbootmicroservice3.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;

    private Role userRole;

    private Long id ;



    //private String name;
}
