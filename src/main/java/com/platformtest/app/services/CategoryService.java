package com.platformtest.app.services;

import com.platformtest.app.domain.Category;
import com.platformtest.app.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public Category findById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    public void delete(String id) {
        categoryRepository.deleteById(id);
    }
    public Category insertNew(Category category) {
        return categoryRepository.insert(category);
    }
    public List<Category> findByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }
}
