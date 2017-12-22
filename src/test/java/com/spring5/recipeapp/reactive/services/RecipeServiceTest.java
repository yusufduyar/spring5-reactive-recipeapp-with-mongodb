package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.converters.RecipeCommandToRecipe;
import com.spring5.recipeapp.reactive.converters.RecipeToRecipeCommand;
import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.exceptions.NotFoundException;
import com.spring5.recipeapp.reactive.repositories.IRecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {
    RecipeService recipeService;

    @Mock
    IRecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeService(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    public void getRecipeByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById("1");

        assertNotNull("Null recipe returned",returnedRecipe);
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository,never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeById_throws_NotFoundException() throws Exception{
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        Recipe returnedRecipe = recipeService.findById("1");
    }

    @Test
    public void getRecipesTest() throws Exception {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(),1);
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    public void recipe_service_deletes_by_id() throws Exception{
        //given
        String idValue = "1";

        //when
        recipeService.deleteById(idValue);

        //then
        verify(recipeRepository,times(1)).deleteById(anyString());
    }
}