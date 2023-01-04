package com.projects.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Integer productId;
    private @NotNull String productName;
    private @NotNull String description;
    private @NotNull String imageUrl;
    private @NotNull BigDecimal price;
    private Integer categoryId;
}