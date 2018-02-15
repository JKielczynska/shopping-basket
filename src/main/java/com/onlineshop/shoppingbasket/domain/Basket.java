package com.onlineshop.shoppingbasket.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "baskets")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "basket_id", unique = true)
    private Long basketId;

    @NotNull
    @Column(name = "open")
    private boolean isOpen;

    @OneToMany(
            targetEntity = BasketProduct.class,
            mappedBy = "basket",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<BasketProduct> basketProducts = new ArrayList<>();

    public Basket(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public Basket(Long basketId, boolean isOpen) {
        this.basketId = basketId;
        this.isOpen = isOpen;
    }
}
