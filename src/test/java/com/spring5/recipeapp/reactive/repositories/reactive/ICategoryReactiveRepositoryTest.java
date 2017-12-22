package com.spring5.recipeapp.reactive.repositories.reactive;

import com.spring5.recipeapp.reactive.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ICategoryReactiveRepositoryTest {

    @Autowired
    ICategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void saveCategoryTest() throws Exception{
        Category category = new Category();
        category.setDescription("foo");

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L),count);
    }

    @Test
    public void findByDescription() throws Exception {
        Category category = new Category();
        category.setDescription("foo");

        categoryReactiveRepository.save(category).block();

        Category selectedCat = categoryReactiveRepository.findByDescription("foo").block();

        assertNotNull(selectedCat.getId());
    }

}