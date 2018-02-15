package com.onlineshop.shoppingbasket.discountstrategy;

import com.onlineshop.shoppingbasket.domain.BasketProduct;
import com.onlineshop.shoppingbasket.domain.Product;
import com.onlineshop.shoppingbasket.domain.dao.BasketProductDao;
import com.onlineshop.shoppingbasket.domain.dao.ProductDao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndividualDiscount implements DiscountStrategy {
    @Autowired
    private BasketProductDao basketProductDao;
    @Autowired
    private ProductDao productDao;

    private BigDecimal getProductPrice(Long productId, Integer quantity) {
        Product product = productDao.findOne(productId);
        BigDecimal productPrice;
        if (quantity < product.getQuantityWithDiscount()) {
            productPrice = product.getRegularPrice().multiply(new BigDecimal(quantity));
        } else if (quantity == product.getQuantityWithDiscount()){
            productPrice = product.getDiscountedPrice();
        } else {
            productPrice = product.getDiscountedPrice().add(product.getRegularPrice().multiply(new BigDecimal(quantity).subtract(new BigDecimal(product.getQuantityWithDiscount()))));
        }
        return productPrice;
    }

    public BigDecimal calculateDiscount(final Long basketId) {
        List<BasketProduct> basketProducts = basketProductDao.findAll();
        BigDecimal totalPrice = new BigDecimal(0.0);
        for (BasketProduct basketProduct : basketProducts) {
            BigDecimal unitPrice = getProductPrice(basketProduct.getProduct().getProductId(), basketProduct.getProduct().getQuantityWithDiscount());
            totalPrice = totalPrice.add(unitPrice);
        }
        return totalPrice;
    }

}
