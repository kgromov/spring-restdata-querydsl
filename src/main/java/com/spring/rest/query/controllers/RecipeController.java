package com.spring.rest.query.controllers;

import com.querydsl.core.types.Predicate;
import com.spring.rest.query.domain.Recipe;
import com.spring.rest.query.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeRepository recipeRepository;

    @GetMapping("/recipes")
    public Iterable<Recipe> findRecipesByQueryDsl(@QuerydslPredicate(root = Recipe.class) Predicate predicate) {
        return recipeRepository.findAll(predicate);
    }
}
