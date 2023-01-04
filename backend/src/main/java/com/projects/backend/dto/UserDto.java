package com.projects.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Integer userId;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String role;

}