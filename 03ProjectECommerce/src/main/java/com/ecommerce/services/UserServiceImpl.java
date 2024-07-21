package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entities.User;
import com.ecommerce.mappers.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final String LOGGER_GET_ALL_USERS = "Fetching all users";
	private static final String LOGGER_GET_USER_BY_ID = "Fetching the user with userId: {}";
	private static final String LOGGER_GET_USER_BY_ID_FAIL = "Fail, user not found";
	private static final String LOGGER_ADD_USER_START = "Adding {}...";
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
	public List<UserDTO> getAllUsers() {
		logger.info(LOGGER_GET_ALL_USERS);
		List<UserDTO> userDTOList = this.userDAORepository.findAll()
				.stream().map(user -> UserMapper.fromUserToUserDTO(user))
				.collect(Collectors.toList());
		
		return userDTOList;
	}

	@Override
	public UserDTO getUserById(Long userId) {		
		Optional<User> tempUser = this.userDAORepository.findById(userId);
		
		if(tempUser.isPresent()) {
			UserDTO userDTO = UserMapper.fromUserToUserDTO(tempUser.get());
			
			logger.info(LOGGER_GET_USER_BY_ID, userId);
			
			return userDTO;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, userId);
			
			throw new NoSuchElementException("User with userId: " + userId + "not found...");
		}
	}

	@Override
	@Transactional
	public UserDTO addUser(UserDTO userDTO) {
		User user = UserMapper.fromUserDTOToUser(userDTO);
		
		logger.info(LOGGER_ADD_USER_START, userDTO.toString());
		this.userDAORepository.save(user);
		logger.info(LOGGER_ADD_USER_SUCCESS, user.getId());
		
		return userDTO;
	}

	@Override
	@Transactional
	public UserDTO updateUser(UserDTO userDTO) {
		logger.info(LOGGER_UPDATE_USER_START, userDTO.getId());
		
		Optional<User> tempUser = this.userDAORepository.findById(userDTO.getId());

		if(tempUser.isPresent()) {	
			User user = UserMapper.fromUserDTOToUser(userDTO);
			
			this.userDAORepository.save(user);
			logger.info(LOGGER_UPDATE_USER_SUCCESS, user.getId());
			
			return userDTO;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, userDTO.getId());
			
			throw new NoSuchElementException("User with userId: " + userDTO.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public String deleteUser(Long userId) {
		logger.info(LOGGER_DELETE_USER_START, userId);
		
		Optional<User> tempUser = this.userDAORepository.findById(userId);
		
		if(tempUser.isPresent()) {
			UserDTO userDTO = UserMapper.fromUserToUserDTO(tempUser.get());
			String deletionMessage = userDTO.toStringUserDeleted();
			
			this.userDAORepository.deleteById(userId);
			userDTO = null;
			logger.info(LOGGER_DELETE_USER_SUCCESS, userId);
			
			return deletionMessage;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, userId);
			
			throw new NoSuchElementException("User with userId: " + userId + "not found...");
		}
	}
}
