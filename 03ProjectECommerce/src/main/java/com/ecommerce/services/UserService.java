package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.User;

public interface UserService {

	/**
	 * CRUD operations
	 * @return
	 */
	
	List<User> getAllUsers();
	User getUserById(Long id);
	User addUser(User user);
	User updateUser(User user);
	User deleteUser(Long id);
}
