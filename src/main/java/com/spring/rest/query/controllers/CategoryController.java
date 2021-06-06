package com.spring.rest.query.controllers;

import com.querydsl.core.types.Predicate;
import com.spring.rest.query.domain.Category;
import com.spring.rest.query.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public Iterable<Category> findCategoriesByQueryDsl(@QuerydslPredicate(root = Category.class) Predicate predicate) {
        return categoryRepository.findAll(predicate);
    }
}
