package com.platformtest.app.services;

import java.util.List;
import java.util.Optional;

import com.platformtest.app.domain.User;
import com.platformtest.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.exception.FieldsEmpty;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.AsksRepository;

@Service
public class AsksService {

	private final AsksRepository repoAsk;
	private final UserRepository repoUser;

	public AsksService(AsksRepository repoAsk, UserRepository repoUser) {
		this.repoAsk = repoAsk;
		this.repoUser = repoUser;
	}
	
	public List<Asks> findAll() {
        return repoAsk.findAll();
	}
	
	public Optional<Asks> findById(String id) {
		if (id.isEmpty()) {
			throw new FieldsEmpty("Não existe nada com esse id.");
		}
		Optional<Asks> existAsk = repoAsk.findById(id);
		if (existAsk.isEmpty()) {
			throw new IdNotFound("Não foi possível localizar nenhum id");
		}
		return existAsk;
	}

	public Optional<User> findUserById(String id) {
        return (Optional<User>) repoUser.findById(id);
	}

	public void delete(String id) {
		repoAsk.deleteById(id);
	}
	public Asks insertNewAsk(Asks ask) {
		return repoAsk.insert(ask);
	}
}
