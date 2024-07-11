package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.services.UserService;

@RestController
@RequestMapping("api")
public class UserREST {

	@Autowired
	private UserService userService;
	
	@GetMapping("users")
	public ResponseEntity<List<UserDTO>> getAllUsersREST() {
		List<UserDTO> userDTOList = userService.getAllUsers()
				.stream().map(user -> UserMapper.fromUserToUserDTO(user))
				.collect(Collectors.toList());
		return new ResponseEntity<>(userDTOList, HttpStatus.OK);
	}
	
	@GetMapping("users/{userId}")
	public ResponseEntity<UserDTO> getUserByIdREST(@PathVariable("userId") Long userId) {
		try {
			UserDTO userDTO = UserMapper.fromUserToUserDTO(userService.getUserById(userId));
			return new ResponseEntity<>(userDTO, HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@PostMapping("users")
	public ResponseEntity<Void> addUserREST(@RequestBody UserDTO userDTO) {
		userService.addUser(UserMapper.fromUserDTOToUser(userDTO));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("users")
	public ResponseEntity<Void> updateUserREST(@RequestBody UserDTO userDTO) {
		try {
			userService.updateUser(UserMapper.fromUserDTOToUser(userDTO));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);								
		}
	}
	
	@DeleteMapping("users/{userId}")
	public ResponseEntity<Void> deleteUserREST(@PathVariable("userId") Long userId) {
		try {
			userService.deleteUser(userId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
}
