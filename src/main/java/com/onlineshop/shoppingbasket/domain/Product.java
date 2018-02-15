package com.onlineshop.shoppingbasket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "product_id", unique = true)
    private Long productId;

    @Column(name = "product_name")
    private String name;

    @Column(name = "regular_price")
    private BigDecimal regularPrice;

    @Column(name = "quantity_with_discount")
    private Integer quantityWithDiscount;

    @Column(name = "discounted_price")
    private BigDecimal discountedPrice;

    @JsonIgnore
    @OneToMany(
            targetEntity = BasketProduct.class,
            mappedBy = "product")
    private List<BasketProduct> basketProducts;

    public Product(final Long productId, final String name, final BigDecimal regularPrice, final Integer quantityWithDiscount, final BigDecimal discountedPrice) {
        this.productId = productId;
        this.name = name;
        this.regularPrice = regularPrice;
        this.quantityWithDiscount = quantityWithDiscount;
        this.discountedPrice = discountedPrice;
    }
}
