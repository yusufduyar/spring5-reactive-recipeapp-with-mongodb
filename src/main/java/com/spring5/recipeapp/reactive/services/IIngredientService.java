package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.IngredientCommand;

public interface IIngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId,String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(String recipeId,String ingredientId);
}
