package com.platformtest.app.controller;

import java.util.List;
import java.util.Optional;

import com.platformtest.app.domain.User;
import com.platformtest.app.dto.DTOAsks;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.UserRepository;
import com.platformtest.app.services.AsksService;
import com.platformtest.app.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.platformtest.app.controller.interfaces.MethodsAsksController;
import com.platformtest.app.domain.Asks;
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

	@GetMapping(value = "/search-all")
	@Override
	public ResponseEntity<List<Asks>> findAll() {
		List<Asks> asks = asksService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(asks);
	}

	@GetMapping(value = "/list-ask/{id}")
	@Override
	public ResponseEntity<DTOAsks> findById(String id) {
		Optional<Asks> existIdAsk = asksService.findById(id);
		if (existIdAsk.isEmpty()) {
			throw  new IdNotFound("Não existe nada com esse id");
		}
		Asks ask = existIdAsk.get();
		String name = ask.getUser().getName();
		DTOAsks dtoAsks = new DTOAsks(ask.getId(), ask.getTitle(), ask.getBodyAsk(), name);
		return ResponseEntity.status(HttpStatus.OK).body(dtoAsks);
	}

	@PostMapping("/new-asks")
	@Override
	public ResponseEntity<String> insertNewAsks(DTOAsks asks, Jwt jwt) {
		String userId = jwt.getSubject();
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User getUser = user.get();
			Asks ask = new Asks(asks.id(), userId, asks.title(), asks.bodyAsk(), getUser);
			ask = asksService.insertNewAsk(ask);
			getUser.getAsks().add(ask);
			userRepository.save(getUser);
			return ResponseEntity.status(HttpStatus.CREATED).body("Nova pergunta criada com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe nenhum usuário vindo de headers com esse id.");
    }
}
