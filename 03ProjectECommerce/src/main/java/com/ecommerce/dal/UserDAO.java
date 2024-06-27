package com.ecommerce.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.User;

public interface UserDAO extends JpaRepository<User, Long> {

}
