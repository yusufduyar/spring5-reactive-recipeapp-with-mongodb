package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.RecipeCommand;
import com.spring5.recipeapp.reactive.converters.RecipeToRecipeCommand;
import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.repositories.IRecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IRecipeServiceIT {

    @Autowired
    IRecipeService recipeService;

    @Autowired
    private IRecipeRepository recipeRepository;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    public void recipe_service_saves_recipe_command() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        //when
        recipeCommand.setDescription("newDescription");
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertNotNull(savedRecipeCommand);
        assertEquals("newDescription", savedRecipeCommand.getDescription());
        assertEquals(recipe.getId(), savedRecipeCommand.getId());
        assertEquals(recipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(recipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }

    @Test
    @DirtiesContext
    public void deleteById_deletes_recipe_by_its_id() {
            Iterable<Recipe> recipes = recipeRepository.findAll();
            Recipe firstRecipe = recipes.iterator().next();
            String recipeIdToDelete = firstRecipe.getId();

            recipeService.deleteById(recipeIdToDelete);

            Optional<Recipe> recipeAfterDelete = recipeRepository.findById(recipeIdToDelete);

            assertEquals(recipeAfterDelete,Optional.empty());

    }
}