package com.spring5.recipeapp.reactive.repositories.reactive;

import com.spring5.recipeapp.reactive.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class IRecipeReactiveRepositoryTest {

    @Autowired
    IRecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void recipeSaveTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setDescription("recipeDesc");

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L),count);
    }

}