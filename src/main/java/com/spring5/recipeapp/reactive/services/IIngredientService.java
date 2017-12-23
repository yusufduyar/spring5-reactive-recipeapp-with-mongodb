package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IIngredientService {
    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);

    Mono<Void> deleteById(String recipeId, String ingredientId);
}
