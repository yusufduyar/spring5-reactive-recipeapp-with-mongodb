package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.RecipeCommand;
import com.spring5.recipeapp.reactive.converters.RecipeCommandToRecipe;
import com.spring5.recipeapp.reactive.converters.RecipeToRecipeCommand;
import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.repositories.reactive.IRecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeService implements IRecipeService {

    private final IRecipeReactiveRepository recipeReactiveRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeService(IRecipeReactiveRepository recipeReactiveRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
       return recipeReactiveRepository.save(recipeCommandToRecipe.convert(recipeCommand))
               .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {

        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                   RecipeCommand command = recipeToRecipeCommand.convert(recipe);

                   command.getIngredients().forEach(recipeCommand -> {
                       recipeCommand.setRecipeId(command.getId());
                   });

                   return command;
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
       recipeReactiveRepository.deleteById(id).block();

       return Mono.empty();
    }
}
