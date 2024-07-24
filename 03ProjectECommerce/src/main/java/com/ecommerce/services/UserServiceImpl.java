package com.ecommerce.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.constants.GlobalConstants;
import com.ecommerce.customexceptions.LessThanEighteenYearsOldException;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entities.User;
import com.ecommerce.mappers.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDAORepository userDAORepository;

    public UserServiceImpl(UserDAORepository userDAORepository) {
        this.userDAORepository = userDAORepository;
    }

	@Override
	public List<UserDTO> getAllUsers() {
		logger.info(GlobalConstants.LOGGER_GET_ALL_USERS);
		List<UserDTO> userDTOList = this.userDAORepository.findAll()
				.stream().map(user -> UserMapper.fromUserToUserDTO(user))
				.collect(Collectors.toList());
		
		return userDTOList;
	}

	@Override
	public UserDTO getUserById(Long userId) {
		if(userId == null) {
			throw new NullPointerException(GlobalConstants.USER_ID_NULL);
		}
		
		Optional<User> tempUser = this.userDAORepository.findById(userId);
		
		if(tempUser.isPresent()) {
			UserDTO userDTO = UserMapper.fromUserToUserDTO(tempUser.get());
			
			logger.info(GlobalConstants.LOGGER_GET_USER_BY_ID, userId);
			
			return userDTO;
		} else {
			logger.error(GlobalConstants.LOGGER_GET_USER_BY_ID_FAIL, userId);
			
			throw new NoSuchElementException("User with userId: " + userId + "not found...");
		}
	}

	@Override
	@Transactional
	public UserDTO addUser(UserDTO userDTO) {
		if(userDTO == null) {
			throw new NullPointerException(GlobalConstants.USER_NULL);
		}
		
		Long eighteenYearsOldLong = 568025136000L;
		Long todayDateLong = Instant.now().toEpochMilli();
		Long userDateOfBirthLong = LocalDate.parse(userDTO.getDateOfBirth().toString()).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
		User user = UserMapper.fromUserDTOToUser(userDTO);
		
		if(todayDateLong - userDateOfBirthLong >= eighteenYearsOldLong) {
			logger.info(GlobalConstants.LOGGER_ADD_USER_START, userDTO.toString());
			this.userDAORepository.save(user);
			logger.info(GlobalConstants.LOGGER_ADD_USER_SUCCESS, user.getId());
			
			return userDTO;
		} else {
			logger.error(GlobalConstants.LOGGER_ADD_USER_FAIL, user.getId());
			
			throw new LessThanEighteenYearsOldException(GlobalConstants.INVALID_DATE_OF_BIRTH_YOUNGER_THAN_18);
		}
	}

	@Override
	@Transactional
	public UserDTO updateUser(UserDTO userDTO) {
		if(userDTO == null) {
			throw new NullPointerException(GlobalConstants.USER_NULL);
		}
		
		logger.info(GlobalConstants.LOGGER_UPDATE_USER_START, userDTO.getId());
		
		Optional<User> tempUser = this.userDAORepository.findById(userDTO.getId());

		if(tempUser.isPresent()) {	
			User user = UserMapper.fromUserDTOToUser(userDTO);
			
			this.userDAORepository.save(user);
			logger.info(GlobalConstants.LOGGER_UPDATE_USER_SUCCESS, user.getId());
			
			return userDTO;
		} else {
			logger.error(GlobalConstants.LOGGER_GET_USER_BY_ID_FAIL, userDTO.getId());
			
			throw new NoSuchElementException("User with userId: " + userDTO.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public String deleteUser(Long userId) {
		if(userId == null) {
			throw new NullPointerException(GlobalConstants.USER_ID_NULL);
		}
		
		logger.info(GlobalConstants.LOGGER_DELETE_USER_START, userId);
		
		Optional<User> tempUser = this.userDAORepository.findById(userId);
		
		if(tempUser.isPresent()) {
			UserDTO userDTO = UserMapper.fromUserToUserDTO(tempUser.get());
			String deletionMessage = userDTO.toStringUserDeleted();
			
			this.userDAORepository.deleteById(userId);
			userDTO = null;
			logger.info(GlobalConstants.LOGGER_DELETE_USER_SUCCESS, userId);
			
			return deletionMessage;
		} else {
			logger.error(GlobalConstants.LOGGER_GET_USER_BY_ID_FAIL, userId);
			
			throw new NoSuchElementException("User with userId: " + userId + "not found...");
		}
	}
}
