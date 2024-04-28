package com.platformtest.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.platformtest.app.domain.User;
import com.platformtest.app.dto.DTOAsks;
import com.platformtest.app.dto.responses.AskListUserName;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.AsksRepository;
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
	private final AsksRepository asksRepository;

	public AsksController(UserServices userServices, AsksService asksService, UserRepository userRepository, AsksRepository asksRepository) {
		this.userServices = userServices;
		this.asksService = asksService;
		this.userRepository = userRepository;
		this.asksRepository = asksRepository;
	}

	@GetMapping(value = "/search-all")
	@Override
	public ResponseEntity<List<AskListUserName>> findAll() {
		List<Asks> listAllAsks = asksService.findAll();
		List<AskListUserName> result = new ArrayList<>();
		for (Asks ask : listAllAsks) {
			String nameUser = ask.getUser().getEmail();
			AskListUserName dto = new AskListUserName(
					ask.getId(),
					ask.getTitle(),
					ask.getBodyAsk(),
					nameUser
			);
			result.add(dto);
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
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
			Asks ask = new Asks(asks.id(), asks.title(), asks.bodyAsk(), getUser);
			ask = asksService.insertNewAsk(ask);
			getUser.getAsks().add(ask);
			userRepository.save(getUser);
			return ResponseEntity.status(HttpStatus.CREATED).body("Nova pergunta criada com sucesso");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe nenhum usuário vindo de headers com esse id.");
    }

	@PutMapping(value = "/edit-asks/{id}")
	@Override
	public ResponseEntity<String> updateAsks(DTOAsks asks, String id) {
		Optional<Asks> existIdAsk = asksService.findById(id);
		if (existIdAsk.isEmpty()) {
			throw new IdNotFound("Não existe nada com esse id");
		}
		Asks editAsk = existIdAsk.get();
		editAsk.setTitle(asks.title());
		editAsk.setBodyAsk(asks.bodyAsk());
		asksRepository.save(editAsk);
		return ResponseEntity.status(HttpStatus.OK).body("Pergunta editada com sucesso.");
	}

	@DeleteMapping(value = "/delete/{id}")
	@Override
	public ResponseEntity<String> deleteAsks(String id) {
		asksService.delete(id);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deletado com sucesso.");
	}
}
