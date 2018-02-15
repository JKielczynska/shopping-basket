package com.onlineshop.shoppingbasket.domain.dao;

import com.onlineshop.shoppingbasket.domain.Basket;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface BasketDao extends CrudRepository<Basket, Long> {

}
