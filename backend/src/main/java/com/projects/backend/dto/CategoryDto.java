package com.projects.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private Integer categoryId;
    private @NotBlank String categoryName;
    private @NotNull String description;
    private @NotNull String imageUrl;
}
