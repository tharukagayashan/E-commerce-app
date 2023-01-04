package com.projects.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Table(name = "CATEGORY")
public class Category {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "CATEGORY_NAME")
    private @NotBlank String categoryName;

    @Column(name = "DESCRIPTION")
    private @NotEmpty String description;

    @Column(name = "IMAGE_URL")
    private @NotEmpty String imageUrl;
//
//    @OneToMany(mappedBy = "category")
//    private List<Product> product;

}
