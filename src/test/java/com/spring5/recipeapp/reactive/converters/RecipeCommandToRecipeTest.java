package com.spring5.recipeapp.reactive.converters;

import com.spring5.recipeapp.reactive.commands.CategoryCommand;
import com.spring5.recipeapp.reactive.commands.IngredientCommand;
import com.spring5.recipeapp.reactive.commands.NotesCommand;
import com.spring5.recipeapp.reactive.commands.RecipeCommand;
import com.spring5.recipeapp.reactive.domain.Difficulty;
import com.spring5.recipeapp.reactive.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
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

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }


    @Test
    public void when_command_object_is_null_converter_returns_null() {
        assertNull(converter.convert(null));
    }

    @Test
    public void when_command_object_is_not_null_but_empty_converter_returns_empty() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        IngredientCommand ingCommand1 = new IngredientCommand();
        ingCommand1.setId(ingredientId1);

        IngredientCommand ingCommand2 = new IngredientCommand();
        ingCommand2.setId(ingredientId2);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(categoryId1);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(categoryId2);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(notesId);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);
        recipeCommand.setDescription(description);
        recipeCommand.setDirections(directions);
        recipeCommand.setCookTime(cookTime);
        recipeCommand.setPrepTime(prepTime);
        recipeCommand.setServings(servings);
        recipeCommand.setDifficulty(difficulty);
        recipeCommand.setUrl(url);
        recipeCommand.setSource(source);
        recipeCommand.getIngredients().add(ingCommand1);
        recipeCommand.getIngredients().add(ingCommand2);
        recipeCommand.getCategories().add(categoryCommand1);
        recipeCommand.getCategories().add(categoryCommand2);
        recipeCommand.setNotes(notesCommand);

        //when
        Recipe recipe = converter.convert(recipeCommand);

        //then
        assertNotNull(recipe);
        assertEquals(recipeId, recipe.getId());
        assertEquals(cookTime, recipe.getCookTime());
        assertEquals(prepTime, recipe.getPrepTime());
        assertEquals(description, recipe.getDescription());
        assertEquals(difficulty, recipe.getDifficulty());
        assertEquals(directions, recipe.getDirections());
        assertEquals(servings, recipe.getServings());
        assertEquals(source, recipe.getSource());
        assertEquals(url, recipe.getUrl());
        assertEquals(notesId, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}