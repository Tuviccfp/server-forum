package com.platformtest.app.repository;

import com.platformtest.app.domain.Category;
import com.platformtest.app.domain.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findByCategoryName(String categoryName);
    List<Category> findCategoriesByUser(User user);
    Category getCategoryByUserOrCategoryName(User user, @NotNull String categoryName);
}
