package com.platformtest.app.controller.interfaces;

import java.util.List;

import com.platformtest.app.dto.responses.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;

import com.platformtest.app.dto.LoginRequest;
import com.platformtest.app.dto.LoginResponse;
import com.platformtest.app.dto.NewUser;
import com.platformtest.app.dto.UserDTO;

public interface MethodsUserController {
	ResponseEntity<List<UserDTO>> findAll();
	ResponseEntity<UserProfile> findById(@AuthenticationPrincipal Jwt jwt);
	ResponseEntity<String> insertNewUser(@RequestBody NewUser newUser);
	ResponseEntity<LoginResponse> loginAccount(@RequestBody LoginRequest loginRequest);
	ResponseEntity<String> updateUser(@RequestBody NewUser newUser, @AuthenticationPrincipal Jwt jwt);
}
