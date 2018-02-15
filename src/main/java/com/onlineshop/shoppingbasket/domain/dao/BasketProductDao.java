package com.onlineshop.shoppingbasket.domain.dao;

import com.onlineshop.shoppingbasket.domain.BasketProduct;

import javax.transaction.Transactional;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface BasketProductDao extends CrudRepository<BasketProduct, Long> {

    @Override
    List<BasketProduct> findAll();

    BasketProduct findByBasketAndProduct(Long basketId, Long productId);

}
