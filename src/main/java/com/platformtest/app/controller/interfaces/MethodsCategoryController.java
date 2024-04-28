package com.platformtest.app.controller.interfaces;

import com.platformtest.app.domain.Category;
import com.platformtest.app.dto.DTOCategory;
import com.platformtest.app.dto.responses.CategoryListAsks;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MethodsCategoryController {
    ResponseEntity<List<Category>> getAllCategories();
    ResponseEntity<CategoryListAsks> getCategoryById(@PathVariable String id);
    ResponseEntity<String> insertNewCategory(@Valid @RequestBody Category category, @AuthenticationPrincipal Jwt jwt);

}
