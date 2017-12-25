package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.converters.RecipeCommandToRecipe;
import com.spring5.recipeapp.reactive.converters.RecipeToRecipeCommand;
import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.repositories.reactive.IRecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {
    RecipeService recipeService;

    @Mock
    IRecipeReactiveRepository recipeReactiveRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeService(recipeReactiveRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    public void getRecipeByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe returnedRecipe = recipeService.findById("1").block();

        assertNotNull("Null recipe returned",returnedRecipe);
        verify(recipeReactiveRepository,times(1)).findById(anyString());
        verify(recipeReactiveRepository,never()).findAll();
    }

    @Test
    public void getRecipesTest() throws Exception {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));

        List<Recipe> recipes = recipeService.getRecipes().collectList().block();

        assertEquals(recipes.size(),1);
        verify(recipeReactiveRepository,times(1)).findAll();
        verify(recipeReactiveRepository,never()).findById(anyString());
    }

    @Test
    public void recipe_service_deletes_by_id() throws Exception{
        //given
        String idValue = "1";

        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        //when
        recipeService.deleteById(idValue);

        //then
        verify(recipeReactiveRepository,times(1)).deleteById(anyString());
    }
}