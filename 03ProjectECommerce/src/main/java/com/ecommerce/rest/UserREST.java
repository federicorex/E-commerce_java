package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.ecommerce.customexceptions.LessThanEighteenYearsOldException;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class UserREST {

	private UserService userService;
	
	public UserREST(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("users")
	public ResponseEntity<List<UserDTO>> getAllUsersREST() {
		return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping("users/{userId}")
	public ResponseEntity<UserDTO> getUserByIdREST(@PathVariable("userId") Long userId) {
		try {
			return new ResponseEntity<>(this.userService.getUserById(userId), HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		} catch(NullPointerException nullPointerException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("users")
	public ResponseEntity<String> addUserREST(@Valid @RequestBody UserDTO userDTO) {
		try {
			this.userService.addUser(userDTO);
			
			return new ResponseEntity<>(userDTO.toStringUserCreatedOrUpdated(), HttpStatus.CREATED);
		} catch(LessThanEighteenYearsOldException lessThanEighteenYearsOld) {
			return new ResponseEntity<>(lessThanEighteenYearsOld.getMessage(), HttpStatus.BAD_REQUEST);
		} catch(NullPointerException nullPointerException) {
		return new ResponseEntity<>(nullPointerException.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("users")
	public ResponseEntity<String> updateUserREST(@Valid @RequestBody UserDTO userDTO) {
		try {
			this.userService.updateUser(userDTO);
			
			return new ResponseEntity<>(userDTO.toStringUserCreatedOrUpdated(), HttpStatus.OK);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(noSuchElementException.getMessage(), HttpStatus.NOT_FOUND);								
		} catch(NullPointerException nullPointerException) {
			return new ResponseEntity<>(nullPointerException.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("users/{userId}")
	public ResponseEntity<Void> deleteUserREST(@PathVariable("userId") Long userId) {
		try {
			this.userService.deleteUser(userId);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch(NullPointerException nullPointerException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
