package com.platformtest.app.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.domain.Category;
import com.platformtest.app.dto.responses.DataExtraProfile;
import com.platformtest.app.dto.responses.UserProfile;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.AsksRepository;
import com.platformtest.app.repository.CategoryRepository;
import com.platformtest.app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

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

	private final UserServices service;
	private final SecurityConfig config;
    private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final AsksRepository asksRepository;

	public UserController(UserServices service, SecurityConfig config, UserRepository userRepository, CategoryRepository categoryRepository, AsksRepository asksRepository) {
		this.service = service;
		this.config = config;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.asksRepository = asksRepository;
	}

	@Override
	public ResponseEntity<List<UserDTO>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping(value = "/profile-data")
	@Override
	public ResponseEntity<UserProfile> findById(Jwt jwt) {
		Optional<User> user = userRepository.findById(jwt.getSubject());
		if (user.isEmpty()) {
			throw new IdNotFound("Não foi possível localizar o id da autentificação");
		}
		UserProfile userDetails = new UserProfile(user.get().getId(), user.get().getEmail(), user.get().getName(), user.get().getRole());
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}

	@GetMapping(value = "/get-datas/{id}")
	public ResponseEntity<DataExtraProfile> findById(@PathVariable("id") String id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new IdNotFound("Erro ao localizar algo com esse id");
		}
		List<Category> dataPublishByUser = categoryRepository.findCategoriesByUser(user.get());
		List<Asks> dataAsksByUser = asksRepository.findAsksByUser(user.get());
		DataExtraProfile getDataProfile = new DataExtraProfile(dataAsksByUser, dataPublishByUser);
		return new ResponseEntity<>(getDataProfile, HttpStatus.OK);
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
		var expiresIn = 3600L;
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

	@PutMapping(value = "/edit-user")
	@Override
	public ResponseEntity<String> updateUser(NewUser newUser, Jwt jwt) {
		Optional<User> userOptional = service.findById(jwt.getSubject());
		if (userOptional.isEmpty()) {
			throw new IdNotFound("Ops.. Houve algo de errado.");
		}
		User editUser = userOptional.get();
		editUser.setEmail(newUser.email());
		editUser.setPassword(PassEncoder.encodePassword(newUser.password()));
		userRepository.save(editUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("Valores alterados com sucesso.");
	}
}
