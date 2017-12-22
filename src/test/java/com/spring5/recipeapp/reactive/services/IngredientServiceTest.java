package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.IngredientCommand;
import com.spring5.recipeapp.reactive.converters.IngredientCommandToIngredient;
import com.spring5.recipeapp.reactive.converters.IngredientToIngredientCommand;
import com.spring5.recipeapp.reactive.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.recipeapp.reactive.domain.Ingredient;
import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.repositories.IIngredientRepository;
import com.spring5.recipeapp.reactive.repositories.IRecipeRepository;
import com.spring5.recipeapp.reactive.repositories.IUnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IngredientServiceTest {
    @Mock
    IRecipeRepository recipeRepository;

    @Mock
    IIngredientRepository ingredientRepository;

    IngredientService ingredientService;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    IUnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientService(ingredientToIngredientCommand, recipeRepository,
                unitOfMeasureToUnitOfMeasureCommand, ingredientCommandToIngredient, unitOfMeasureRepository, ingredientRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("1");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3");

        //when
        assertEquals("3", ingredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
    }

    @Test
    public void saveIngredientCommandTest() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("1");
        ingredientCommand.setRecipeId("2");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("1");

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);
        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals("1", savedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any());
    }

    @Test
    public void deleteIngredient_should_delete_ingredient_by_id() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById("1","3");

        //then
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}