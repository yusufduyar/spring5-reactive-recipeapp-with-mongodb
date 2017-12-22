package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.RecipeCommand;
import com.spring5.recipeapp.reactive.domain.Recipe;

import java.util.Set;

public interface IRecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(String id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(String id);

    void deleteById(String id);
}
