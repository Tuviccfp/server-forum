package com.platformtest.app.controller;

import java.util.List;
import java.util.Optional;

import com.platformtest.app.domain.User;
import com.platformtest.app.dto.DTOAsks;
import com.platformtest.app.repository.UserRepository;
import com.platformtest.app.services.AsksService;
import com.platformtest.app.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.platformtest.app.controller.interfaces.MethodsAsksController;
import com.platformtest.app.domain.Asks;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/asks")
public class AsksController implements MethodsAsksController {


	private final UserServices userServices;
	private final AsksService asksService;
	private final UserRepository userRepository;

	public AsksController(UserServices userServices, AsksService asksService, UserRepository userRepository) {
		this.userServices = userServices;
		this.asksService = asksService;
		this.userRepository = userRepository;
	}

	@Override
	public ResponseEntity<List<Asks>> findAll() {
		List<Asks> asks = asksService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(asks);
	}

//	@Override
//	public ResponseEntity<AsksDTO> findById(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@PostMapping(value = "/register-test")
	public ResponseEntity<String> insertTest(@AuthenticationPrincipal Jwt jwt, @RequestBody Asks asks) {
		String id = "";
		Asks ask = new Asks(asks.getId(), id, asks.getTitle(), asks.getBodyAsk());
		ask = asksService.insertNewAsk(ask);
		return ResponseEntity.ok("Criado com sucesso");
	}

	@PostMapping("/new-asks")
	@Override
	public ResponseEntity<String> insertNewAsks(DTOAsks asks, Jwt jwt) {
		String userId = jwt.getSubject();
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User getUser = user.get();
			Asks ask = new Asks(asks.id(), userId, asks.title(), asks.bodyAsk());
			ask = asksService.insertNewAsk(ask);
			getUser.getAsks().add(ask);
			userRepository.save(getUser);
			return ResponseEntity.status(HttpStatus.CREATED).body("Nova pergunta criada com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe nenhum usuário vindo de headers com esse id.");
    }

	@GetMapping(value = "/return-user-by-id")
	public ResponseEntity<User> returnId(@AuthenticationPrincipal Jwt jwt) {
		String id = jwt.getSubject();
		Optional<User> user = userServices.findById(id);
		return ResponseEntity.ok(user.get());
	}



}
