package com.spring.rest.query.repositories;

import com.querydsl.core.types.dsl.StringPath;
import com.spring.rest.query.domain.QRecipe;
import com.spring.rest.query.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface RecipeRepository extends JpaRepository<Recipe, Long>,
    QuerydslPredicateExecutor<Recipe>, QuerydslBinderCustomizer<QRecipe> {

    @Override
    default void customize(QuerydslBindings bindings, QRecipe root) {
        bindings.bind(String.class)
            .first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(root.directions);
        bindings.excluding(root.image);
    }
}
