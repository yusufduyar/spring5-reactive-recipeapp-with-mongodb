package com.spring5.recipeapp.reactive.converters;

import com.spring5.recipeapp.reactive.commands.RecipeCommand;
import com.spring5.recipeapp.reactive.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    private static final String recipeId = "1";
    private static final Integer cookTime = Integer.valueOf("5");
    private static final Integer prepTime = Integer.valueOf("7");
    private static final String description = "My Recipe";
    private static final String directions = "Directions";
    private static final Difficulty difficulty = Difficulty.EASY;
    private static final Integer servings = Integer.valueOf("3");
    private static final String source = "Source";
    private static final String url = "Some URL";
    private static final String categoryId1 = "1";
    private static final String categoryId2 = "2";
    private static final String ingredientId1 = "3";
    private static final String ingredientId2 = "4";
    private static final String notesId = "9";

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand());
    }


    @Test
    public void when_command_object_is_null_converter_returns_null() {
        assertNull(converter.convert(null));
    }

    @Test
    public void when_command_object_is_not_null_but_empty_converter_returns_empty() {
        assertNotNull(converter.convert(new Recipe()));
    }


    @Test
    public void convert() throws Exception {
        //given
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ingredientId1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredientId2);

        Category category1 = new Category();
        category1.setId(categoryId1);

        Category category2 = new Category();
        category2.setId(categoryId2);

        Notes notes = new Notes();
        notes.setId(notesId);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.setDescription(description);
        recipe.setDirections(directions);
        recipe.setCookTime(cookTime);
        recipe.setPrepTime(prepTime);
        recipe.setServings(servings);
        recipe.setDifficulty(difficulty);
        recipe.setUrl(url);
        recipe.setSource(source);
        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);
        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);
        recipe.setNotes(notes);

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertNotNull(recipe);
        assertEquals(recipeId, recipeCommand.getId());
        assertEquals(cookTime, recipeCommand.getCookTime());
        assertEquals(prepTime, recipeCommand.getPrepTime());
        assertEquals(description, recipeCommand.getDescription());
        assertEquals(difficulty, recipeCommand.getDifficulty());
        assertEquals(directions, recipeCommand.getDirections());
        assertEquals(servings, recipeCommand.getServings());
        assertEquals(source, recipeCommand.getSource());
        assertEquals(url, recipeCommand.getUrl());
        assertEquals(notesId, recipeCommand.getNotes().getId());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
    }

}