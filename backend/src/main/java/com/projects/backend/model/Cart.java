package com.projects.backend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Table(name = "CART")
public class Cart {

    @Id
    @Column(name = "CART_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @Column(name = "ITEM_COUNT")
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false, referencedColumnName = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, referencedColumnName = "USER_ID")
    private User user;

}
