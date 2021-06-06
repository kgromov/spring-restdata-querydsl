package com.spring.rest.query.controllers;

import com.querydsl.core.types.Predicate;
import com.spring.rest.query.domain.Category;
import com.spring.rest.query.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
    @InjectMocks private CategoryController testee;
    @Mock private CategoryRepository categoryRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = standaloneSetup(testee).build();
    }

    @Test
    public void findCategoriesByQueryDsl() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Ukrainian food");
        when(categoryRepository.findAll(any(Predicate.class))).thenReturn(Collections.singleton(category));

        mockMvc.perform(get("/categories").param("description", "American"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.id", equalTo(1L)))
            .andExpect(jsonPath("$.description", equalTo("Ukrainian food")));

    }
}
