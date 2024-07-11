package com.ecommerce.mappers;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.entities.User;

public class UserMapper {
	
	public static UserDTO fromUserToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setSurname(user.getSurname());
		userDTO.setDateOfBirth(user.getDateOfBirth());
		userDTO.setAddress(user.getAddress());
		userDTO.setEmail(user.getEmail());
		userDTO.setOrders(user.getOrders());
		
		return userDTO;
	}
	
	public static User fromUserDTOToUser(UserDTO userDTO) {
		User user = new User();
		
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		user.setPassword(userDTO.getPassword());
		user.setDateOfBirth(userDTO.getDateOfBirth());
		user.setAddress(userDTO.getAddress());
		user.setEmail(userDTO.getEmail());
		user.setOrders(userDTO.getOrders());
		
		return user;
	}
}
