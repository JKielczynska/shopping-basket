package com.onlineshop.shoppingbasket.service;

import com.onlineshop.shoppingbasket.discountstrategy.DiscountStrategy;
import com.onlineshop.shoppingbasket.domain.Basket;
import com.onlineshop.shoppingbasket.domain.BasketProduct;
import com.onlineshop.shoppingbasket.domain.dao.BasketDao;
import com.onlineshop.shoppingbasket.domain.dao.BasketProductDao;
import com.onlineshop.shoppingbasket.domain.dao.ProductDao;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BasketDao basketDao;
    @Autowired
    private BasketProductDao basketProductDao;
    @Autowired
    private DiscountStrategy discountStrategy;

    public Basket openNewBasket() {
        Basket basket = new Basket();
        basket.setOpen(true);
        basket = basketDao.save(basket);
        return basket;
    }

    public Basket addProductToBasket(Long basketId, Long productId, Integer quantity) {
        Basket basket = basketDao.findOne(basketId);
        if (basket == null) {
            basket = addProductToNewBasket(productId, quantity);
            return basket;
        } else {
            BasketProduct basketProduct = basketProductDao.findByBasketAndProduct(basketId, productId);
            if (basketProduct == null) {
                basket = addProductToExistingBasket(basket, productId, quantity);
                return basket;
            } else {
                basket = updateProductInBasket(basketId, productId, quantity);
                return basket;
            }
        }
    }

    public Basket updateProductInBasket (Long basketId, Long productId, Integer quantity) {
        Basket basket = basketDao.findOne(basketId);
        for (BasketProduct basketProduct : basket.getBasketProducts()) {
            if (basketProduct.getProduct().getProductId().equals(productId)) {
                basketProduct.setQuantity(quantity);
            }
        }
        return basket;
    }

    public BigDecimal totalPrice(Long basketId) {
        return discountStrategy.calculateDiscount(basketId);
    }

    public void closeBasket(Long basketId) {
        Basket basket = basketDao.findOne(basketId);
        basket.setOpen(false);
    }

    private Basket addProductToNewBasket(Long productId, Integer quantity) {
        Basket basket = new Basket(true);
        BasketProduct basketProduct = new BasketProduct(quantity);
        basketProduct.setProduct(productDao.findOne(productId));
        basket.getBasketProducts().add(basketProduct);
        basketProduct.setBasket(basket);
        basketDao.save(basket);

        return basket;
    }

    private Basket addProductToExistingBasket(Basket basket, Long productId, Integer quantity) {
        BasketProduct basketProduct = new BasketProduct(quantity);
        basketProduct.setProduct(productDao.findOne(productId));
        basket.getBasketProducts().add(basketProduct);
        basketProduct.setBasket(basket);

        return basket;
    }



}
