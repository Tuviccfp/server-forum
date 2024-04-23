package com.platformtest.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.platformtest.app.domain.Asks;
@Repository
public interface AsksRepository extends MongoRepository<Asks, String> {

}
