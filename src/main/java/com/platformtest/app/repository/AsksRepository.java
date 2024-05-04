package com.platformtest.app.repository;

import com.platformtest.app.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platformtest.app.domain.Asks;

import java.util.List;

@Repository
public interface AsksRepository extends MongoRepository<Asks, String> {
    List<Asks> findAsksByUser(User user);
}
