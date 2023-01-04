package com.projects.backend.dto;

import com.projects.backend.model.Product;
import com.projects.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {

    private Integer cartId;
    private Integer count;
    private Integer productId;
    private Integer userId;

    private Product product;
    private User user;

}