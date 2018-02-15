package com.onlineshop.shoppingbasket.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products_in_basket")
public class BasketProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "basket_product_id", unique = true)
    private Long basketProductId;

    @ManyToOne
    @JoinColumn(name ="basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    public BasketProduct(final Integer quantity) {
        this.quantity = quantity;
    }

    public BasketProduct(final Basket basket, final Product product, final Integer quantity) {
        this.basket = basket;
        this.product = product;
        this.quantity = quantity;
    }
}
