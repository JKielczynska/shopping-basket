package com.onlineshop.shoppingbasket.discountstrategy;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal calculateDiscount(Long basketId);
}
