package com.ecommerce.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.User;

@Repository
public interface UserDAORepository extends JpaRepository<User, Long> {

}
