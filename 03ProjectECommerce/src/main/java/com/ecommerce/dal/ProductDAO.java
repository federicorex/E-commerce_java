package com.ecommerce.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {

}
