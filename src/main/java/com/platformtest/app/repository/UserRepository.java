package com.platformtest.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platformtest.app.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

}
