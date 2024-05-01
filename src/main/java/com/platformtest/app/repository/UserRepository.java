package com.platformtest.app.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.platformtest.app.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
//	@Query("{email: '?0'}")
	Optional<User> findByEmail(String email);
}
