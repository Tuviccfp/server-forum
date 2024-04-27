package com.platformtest.app.repository;

import com.platformtest.app.domain.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResponseRepository extends MongoRepository<Response, String> {

}
