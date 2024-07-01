package com.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.UserDAO;
import com.ecommerce.entities.User;

public class UserServiceImpl implements UserService {

	private final UserDAO userDAORepository;

    public UserServiceImpl(UserDAO userDAORepository) {
        this.userDAORepository = userDAORepository;
    }

	@Override
	public List<User> getAllUsers() {
		return userDAORepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userDAORepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void addUser(User user) {
		userDAORepository.save(user);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userDAORepository.save(user);
	}

	@Override
	@Transactional
	public void deleteUser(Long id) {
		userDAORepository.deleteById(id);
	}
	
}
