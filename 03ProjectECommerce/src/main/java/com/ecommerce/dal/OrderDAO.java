package com.ecommerce.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Order;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {

}
