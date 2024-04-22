package com.platformtest.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platformtest.app.controller.interfaces.MethodsUserController;
import com.platformtest.app.dto.UserDTO;
import com.platformtest.app.services.UserServices;

@RestController
@RequestMapping(value = "/api/register")
public class UserController implements MethodsUserController {

	@Autowired
	private UserServices service;

	@Override
	public ResponseEntity<List<UserDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UserDTO> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> insertNewUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
