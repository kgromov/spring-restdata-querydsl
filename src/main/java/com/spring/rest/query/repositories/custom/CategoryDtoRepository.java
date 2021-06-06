package com.spring.rest.query.repositories.custom;

import com.spring.rest.query.domain.dtos.CategoryRecipes;

import java.util.List;

public interface CategoryDtoRepository {

    List<CategoryRecipes> getAllRecipesByCategoryResultSetMapping();

    List<CategoryRecipes> getAllRecipesByCategoryRecordMapper();

    List<CategoryRecipes> getAllRecipesByCategoryFetchTo();
}
