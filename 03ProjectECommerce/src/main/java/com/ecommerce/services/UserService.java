package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.User;

public interface UserService {

	List<User> getAllUsers();
	void addUser();
	void updateUser();
	void deleteUser();
}
