package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.entities.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final String LOGGER_GET_ALL_USERS = "Fetching all users";
	private static final String LOGGER_GET_USER_BY_ID = "Fetching the user with userId: {}";
	private static final String LOGGER_GET_USER_BY_ID_FAIL = "Fail, user not found";
	private static final String LOGGER_ADD_USER_START = "Adding user with userId: {}...";
	private static final String LOGGER_ADD_USER_SUCCESS = "Success, user with userId: {} added";
	private static final String LOGGER_UPDATE_USER_START = "Updating user with userId: {}...";
	private static final String LOGGER_UPDATE_USER_SUCCESS = "Success, user with userId: {} updated";
	private static final String LOGGER_DELETE_USER_START = "Deleting user with userId: {}...";
	private static final String LOGGER_DELETE_USER_SUCCESS = "Success, user with userId: {} deleted";
	
	private UserDAORepository userDAORepository;

    public UserServiceImpl(UserDAORepository userDAORepository) {
        this.userDAORepository = userDAORepository;
    }

	@Override
	public List<User> getAllUsers() {
		logger.info(LOGGER_GET_ALL_USERS);
		return userDAORepository.findAll();
	}

	@Override
	public User getUserById(Long userId) {		
		Optional<User> tempUser = userDAORepository.findById(userId);
		
		if(tempUser.isPresent()) {
			User user = tempUser.get();
			
			logger.info(LOGGER_GET_USER_BY_ID, userId);
			
			return user;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, userId);
			throw new NoSuchElementException("User with userId: " + userId + "not found...");
		}
	}

	@Override
	@Transactional
	public User addUser(User user) {
		logger.info(LOGGER_ADD_USER_START, user.getId());
		userDAORepository.save(user);
		logger.info(LOGGER_ADD_USER_SUCCESS, user.getId());
		
		return user;
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		logger.info(LOGGER_UPDATE_USER_START, user.getId());
		
		Optional<User> tempUser = userDAORepository.findById(user.getId());

		if(tempUser.isPresent()) {	
			userDAORepository.save(user);
			logger.info(LOGGER_UPDATE_USER_SUCCESS, user.getId());
			
			return user;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, user.getId());
			
			throw new NoSuchElementException("User with userId: " + user.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public User deleteUser(Long userId) {
		logger.info(LOGGER_DELETE_USER_START, userId);
		
		Optional<User> tempUser = userDAORepository.findById(userId);
		
		if(tempUser.isPresent()) {
			User user = tempUser.get();
			
			userDAORepository.deleteById(userId);
			logger.info(LOGGER_DELETE_USER_SUCCESS, userId);
			
			return user;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, userId);
			
			throw new NoSuchElementException("User with userId: " + userId + "not found...");
		}
	}
}
