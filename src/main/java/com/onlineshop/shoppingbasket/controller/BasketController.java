package com.onlineshop.shoppingbasket.controller;

import com.onlineshop.shoppingbasket.domain.Basket;
import com.onlineshop.shoppingbasket.service.BasketService;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baskets")
public class BasketController {
    @Autowired
    private BasketService basketService;

    @PostMapping
    public Basket openNewBasket() {
        return basketService.openNewBasket();
    }

    @PostMapping(value = "{basketId}/{productId}/{quantity}")
    public Basket addProductToBasket(@PathVariable("basketId") Long basketId,
                                            @PathVariable("productId") Long productId,
                                            @PathVariable("quantity") Integer quantity) {

        return basketService.addProductToBasket(basketId, productId, quantity);
    }

    @PutMapping(value = "{basketId}/{productId}/{quantity}")
    public Basket updateProductInBasket(@PathVariable("basketId") Long basketId,
                                        @PathVariable("productId") Long productId,
                                        @PathVariable("quantity") Integer quantity) {
        return basketService.updateProductInBasket(basketId, productId, quantity);
    }

    @GetMapping(value = "{basketId}")
    public BigDecimal calculateTotalPriceAndCloseBasket(@PathVariable("basketId") Long basketId) {
        BigDecimal totalPrice = basketService.totalPrice(basketId);
        basketService.closeBasket(basketId);
        return totalPrice;
    }
}
