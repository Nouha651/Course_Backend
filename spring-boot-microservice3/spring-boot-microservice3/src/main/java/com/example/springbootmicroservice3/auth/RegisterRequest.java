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
public class RegisterRequest {

    private String name;

    private String username;

    private String password;

    public Role role;

    private  String position;

    private byte[] profileImage;


}
