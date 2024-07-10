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
	
//	@GetMapping("users")
//	public ResponseEntity<List<User>> getAllUsersREST() {
//		return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
//	}
	
	@GetMapping("users/{id}")
	public ResponseEntity<UserDTO> getUserByIdREST(@PathVariable("id") Long id) {
		try {
			UserDTO userDTO = UserMapper.fromUserToUserDTO(userService.getUserById(id));
			return new ResponseEntity<>(userDTO, HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
//	@GetMapping("users/{id}")
//	public ResponseEntity<User> getUserByIdREST(@PathVariable("id") Long id) {
//		try {
//			return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);			
//		} catch(NoSuchElementException noSuchElementException) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
//		}
//	}
	
	@PostMapping("users")
	public ResponseEntity<Void> addUserREST(@RequestBody UserDTO userDTO) {
		userService.addUser(UserMapper.fromUserDTOToUser(userDTO));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
//	@PostMapping("users")
//	public ResponseEntity<Void> addUserREST(@RequestBody User user) {
//		userService.addUser(user);
//		return new ResponseEntity<>(HttpStatus.CREATED);
//	}
	
	@PutMapping("users")
	public ResponseEntity<Void> updateUserREST(@RequestBody UserDTO userDTO) {
		userService.updateUser(UserMapper.fromUserDTOToUser(userDTO));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
//	@PutMapping("users")
//	public ResponseEntity<Void> updateUserREST(@RequestBody User user) {
//		try {
//			userService.updateUser(user);
//			return new ResponseEntity<>(HttpStatus.OK);			
//		} catch(NoSuchElementException noSuchElementException) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
//		}
//	}
	
	@DeleteMapping("users")
	public ResponseEntity<Void> deleteUserREST(@PathVariable("id") Long id) {
		try {
			userService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
	}
}
