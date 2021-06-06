package com.spring.rest.query.repositories;

import com.querydsl.core.types.dsl.StringPath;
import com.spring.rest.query.domain.Category;
import com.spring.rest.query.domain.QCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>,
    QuerydslPredicateExecutor<Category>, QuerydslBinderCustomizer<QCategory> {

    Optional<Category> findByDescription(String description);

    @Override
    default void customize(QuerydslBindings bindings, QCategory root) {
        bindings.bind(String.class)
            .first((StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.excluding(root.recipes);
    }
}
