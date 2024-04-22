package com.platformtest.app.controller.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.platformtest.app.dto.UserDTO;

public interface MethodsUserController {
	ResponseEntity<List<UserDTO>> findAll();
	ResponseEntity<UserDTO> findById(@PathVariable String id);
	ResponseEntity<String> insertNewUser(@RequestBody UserDTO userDTO);
}
