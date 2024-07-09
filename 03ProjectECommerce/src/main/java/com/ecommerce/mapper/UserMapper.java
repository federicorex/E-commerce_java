package com.ecommerce.mapper;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.entities.User;

public class UserMapper {

	public static UserDTO fromUserToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(user.getId());
		userDTO.setAddress(user.getAddress());
		userDTO.setDateOfBirth(user.getDateOfBirth());
		userDTO.setEmail(user.getEmail());
		userDTO.setName(user.getName());
		userDTO.setOrders(user.getOrders());
		userDTO.setPassword(user.getPassword());
		userDTO.setSurname(user.getSurname());
		
		return userDTO;
	}
	
	public static User fromUserDTOToUser(UserDTO userDTO) {
		User user = new User();
		
		user.setId(userDTO.getId());
		user.setAddress(userDTO.getAddress());
		user.setDateOfBirth(userDTO.getDateOfBirth());
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setOrders(userDTO.getOrders());
		user.setPassword(userDTO.getPassword());
		user.setSurname(userDTO.getSurname());
		
		return user;
	}
}
