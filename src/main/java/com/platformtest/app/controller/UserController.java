package com.platformtest.app.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platformtest.app.controller.interfaces.MethodsUserController;
import com.platformtest.app.domain.User;
import com.platformtest.app.dto.LoginRequest;
import com.platformtest.app.dto.LoginResponse;
import com.platformtest.app.dto.NewUser;
import com.platformtest.app.dto.UserDTO;
import com.platformtest.app.security.SecurityConfig;
import com.platformtest.app.security.config.PassEncoder;
import com.platformtest.app.services.UserServices;

@RestController
@RequestMapping(value = "/api/register")
public class UserController implements MethodsUserController {

	@Autowired
	private UserServices service;
	@Autowired 
	private SecurityConfig config;
	
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

	@PostMapping("/register-new-account")
	@Override
	public ResponseEntity<String> insertNewUser(NewUser newuser) {
		String encoded = PassEncoder.encodePassword(newuser.password());
		User user = new User(newuser.id(), newuser.email(), encoded, newuser.role());
		user = service.insertNewUser(user);
		return ResponseEntity.ok("Usuário salvo com sucesso");
	}

	@PostMapping(value = "/login")
	@Override
	public ResponseEntity<LoginResponse> loginAccount(LoginRequest loginRequest) {
		Optional<User> user = service.findByName(loginRequest.email());
		if (user.isEmpty() || !PassEncoder.matches(loginRequest.password(), user.get().getPassword())) {
			throw new BadCredentialsException("Usuário ou senha não batem");
		}
		var now = Instant.now();
		var expiresIn = 300L;
		var claims = JwtClaimsSet.builder()
				.issuer("myback")
				.subject(user.get().getId())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expiresIn))
				.claim("scope", user.get().getRole())
				.build();
		var jwtValue = config.jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
		return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
	}
}
