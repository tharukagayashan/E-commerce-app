package com.projects.backend.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtDataDto {

    private String username;
    private Integer userId;
    private String iss;

}