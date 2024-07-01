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
	void addUser(User user);
	void updateUser(User user);
	void deleteUser(Long id);
}
