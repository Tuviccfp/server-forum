package com.platformtest.app.repository;

import com.platformtest.app.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    @Query("{categoryName: '?0'}")
    List<Category> findByCategoryName(String categoryName);
}
