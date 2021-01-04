package com.spring.templates.repositories.custom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.templates.domain.QCategory;
import com.spring.templates.domain.QRecipe;
import com.spring.templates.domain.dtos.CategoryRecipes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryDtoRepositoryImpl implements CategoryDtoRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryRecipes> getAllRecipesByCategoryResultSetMapping() {
        throw new UnsupportedOperationException("ResultSetMapping are not supported as query dsl builds jpql based query");
    }

    @Override
    public List<CategoryRecipes> getAllRecipesByCategoryRecordMapper() {
        StopWatch stopWatch = new StopWatch("getAllRecipesByCategoryRecordMapper");
        stopWatch.start("getAllRecipesByCategoryRecordMapper");
        QCategory category = QCategory.category;
        List<Tuple> result = selectRecipesByCategory().fetch();
        List<CategoryRecipes> recipes = result.stream()
            .map(row -> new CategoryRecipes(
                row.get(category.id),
                row.get(category.description),
//                        row.get(2, Long.class)
                row.get(category.count().as("recipes"))
            ))
            .collect(Collectors.toList());
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return recipes;
    }

    @Override
    public List<CategoryRecipes> getAllRecipesByCategoryFetchTo() {
        StopWatch stopWatch = new StopWatch("getAllRecipesByCategoryFetchTo");
        stopWatch.start("getAllRecipesByCategoryFetchTo");
        QCategory category = QCategory.category;
        QRecipe recipe = QRecipe.recipe;

        // Projections.bean - setters
        // Projections.fields - reflection
        // Projections.constructor - if appropriate constructor exists
        // select(new CategoryRecipes()) does not work
        List<CategoryRecipes> recipes = queryFactory.select(Projections.constructor(CategoryRecipes.class,
            category.id, category.description, category.count().as("recipes")))
            .from(category)
            .where(category.recipes.any().in(queryFactory.selectFrom(recipe).fetch()))
            .groupBy(category.id)
            .fetch();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        return recipes;
    }

    private JPAQuery<Tuple> selectRecipesByCategory() {
        QCategory category = QCategory.category;
        QRecipe recipe = QRecipe.recipe;

        /*JPAQuery<Tuple> recipes = queryFactory.select(category.id, category.description, category.count().as("recipes"))
                .from(category)
                .join(category.recipes, recipe)
                .on(category.recipes.contains(recipe))
                .groupBy(category.id);*/

        JPAQuery<Tuple> recipes = queryFactory.select(category.id, category.description, category.count().as("recipes"))
            .from(category)
            .where(category.recipes.any().in(queryFactory.selectFrom(recipe).fetch()))
            .groupBy(category.id);
        log.info("SQL = {}", recipes.toString());
        return recipes;
    }
}
