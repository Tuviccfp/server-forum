package com.platformtest.app.controller;

import com.platformtest.app.controller.interfaces.MethodsCategoryController;
import com.platformtest.app.domain.Category;
import com.platformtest.app.domain.User;
import com.platformtest.app.dto.responses.CategoryBasicResponse;
import com.platformtest.app.dto.responses.CategoryListAsks;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.UserRepository;
import com.platformtest.app.services.CategoryService;
import com.platformtest.app.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController implements MethodsCategoryController {

    private final CategoryService categoryService;
    private final UserServices userServices;
    private final UserRepository userRepository;

    public CategoryController(CategoryService categoryService, UserServices userServices, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.userServices = userServices;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/search-all")
    @Override
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping(value = "/get-category/{id}")
    @Override
    public ResponseEntity<CategoryListAsks> getCategoryById(String id) {
        var category = categoryService.findById(id);
        if (category == null) {
            throw new IdNotFound("Não existe nada com esse id");
        }
        CategoryListAsks list = new CategoryListAsks(category.getId(), category.getCategoryName(), category.getAsks());
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping(value = "/new-category")
    @Override
    public ResponseEntity<String> insertNewCategory(Category category, Jwt jwt) {
        String id = jwt.getSubject();
         var user = userServices.findById(id);
         if (user.isEmpty()) {
             throw new IdNotFound("Não existe nada com esse id");
         }
         User objUser = user.get();
        Category newCategory = new Category(category.getCategoryName(), category.getId(), objUser);
        categoryService.insertNew(newCategory);
         objUser.getCategories().add(newCategory);
        userRepository.save(objUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Categoria publicada com sucesso");
    }
}
