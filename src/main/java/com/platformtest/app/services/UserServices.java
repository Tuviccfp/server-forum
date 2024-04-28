package com.platformtest.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platformtest.app.domain.User;
import com.platformtest.app.exception.FieldsEmpty;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.UserRepository;

@Service
public class UserServices {

	@Autowired
	private UserRepository userRepo;
	
	public User insertNewUser(User user) {
		return userRepo.insert(user);
	}

	public Optional<User> findByName(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<User> findAllUsers() {
		List<User> getUser = userRepo.findAll();
		return getUser;
	}
	
	public Optional<User> findById(String id) {
		if (!id.isEmpty()) {
			Optional<User> existUser = userRepo.findById(id);
			if (!existUser.isPresent()) {
				throw new IdNotFound("Não foi possível localizar o id");
			}
			return existUser;
		} else {
			throw new FieldsEmpty("Não existe nenhum identificador informado por params");
		}
	}
}
