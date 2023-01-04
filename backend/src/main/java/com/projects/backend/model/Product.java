package com.projects.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "PRODUCT_NAME")
    private @NotBlank String productName;

    @Column(name = "DESCRIPTION")
    private @NotEmpty String description;

    @Column(name = "IMAGE_URL")
    private @NotEmpty String imageUrl;

    @Column(name = "PRICE")
    private @NotEmpty BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false, referencedColumnName = "CATEGORY_ID")
    private Category category;

//    @OneToMany(mappedBy = "product")
//    private List<Cart> cart;

}
