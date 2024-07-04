package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.UserDAO;
import com.ecommerce.entities.User;

public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final String LOGGER_GET_ALL_USERS = "Fetching all users";
	private static final String LOGGER_GET_USER_BY_ID = "Fetching the user with id: {}";
	private static final String LOGGER_GET_USER_BY_ID_FAIL = "Fail, user not found";
	private static final String LOGGER_ADD_USER_START = "Adding user with id: {}...";
	private static final String LOGGER_ADD_USER_SUCCESS = "Success, user with id: {} added";
	private static final String LOGGER_UPDATE_USER_START = "Updating user with id: {}...";
	private static final String LOGGER_UPDATE_USER_SUCCESS = "Success, user with id: {} updated";
	private static final String LOGGER_DELETE_USER_START = "Deleting user with id: {}...";
	private static final String LOGGER_DELETE_USER_SUCCESS = "Success, user with id: {} deleted";
	
	private UserDAO userDAORepository;

    public UserServiceImpl(UserDAO userDAORepository) {
        this.userDAORepository = userDAORepository;
    }

	@Override
	public List<User> getAllUsers() {
		logger.info(LOGGER_GET_ALL_USERS);
		return userDAORepository.findAll();
	}

	@Override
	public User getUserById(Long id) {		
		Optional<User> tempUser = userDAORepository.findById(id);
		
		if(tempUser.isPresent()) {
			User user = tempUser.get();
			
			logger.info(LOGGER_GET_USER_BY_ID, id);
			return user;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, id);
			throw new NoSuchElementException("User with id: " + id + "found...");
		}
	}

	@Override
	@Transactional
	public void addUser(User user) {
		logger.info(LOGGER_ADD_USER_START, user.getId());
		userDAORepository.save(user);
		logger.info(LOGGER_ADD_USER_SUCCESS, user.getId());
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		logger.info(LOGGER_UPDATE_USER_START, user.getId());
		userDAORepository.save(user);
		logger.info(LOGGER_UPDATE_USER_SUCCESS, user.getId());
	}

	@Override
	@Transactional
	public void deleteUser(Long id) {
		logger.info(LOGGER_DELETE_USER_START, id);
		
		Optional<User> tempUser = userDAORepository.findById(id);
		
		if(tempUser.isPresent()) {	
			userDAORepository.deleteById(id);
			logger.info(LOGGER_DELETE_USER_SUCCESS, id);
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, id);
			throw new NoSuchElementException("User with id: " + id + "found...");
		}
	}
	
}
