package com.onlineshop.shoppingbasket.domain.dao;

import com.onlineshop.shoppingbasket.domain.Product;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

}
