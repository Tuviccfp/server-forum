package com.platformtest.app.controller;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.domain.Category;
import com.platformtest.app.domain.User;
import com.platformtest.app.repository.UserRepository;
import com.platformtest.app.services.CategoryService;
import com.platformtest.app.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/funcionalities")
public class FuncUserController {

    private final UserServices userServices;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public FuncUserController(UserServices userServices, UserRepository userRepository, CategoryService categoryService) {
        this.userServices = userServices;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Category>> findAllByName(@RequestBody String name) {
        List<Category> categories = categoryService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping
    public ResponseEntity<List<Asks>> findAllAskByHeaderId(@AuthenticationPrincipal Jwt jwt) {
        String id = jwt.getSubject();
        Optional<User> existUser = userServices.findById(id);
//        if (existUser.isPresent()) {}
        User user = existUser.get();
        return ResponseEntity.status(HttpStatus.OK).body(user.getAsks());
    }

}
