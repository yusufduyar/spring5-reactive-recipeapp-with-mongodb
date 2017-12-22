package com.spring5.recipeapp.reactive.repositories.reactive;

import com.spring5.recipeapp.reactive.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IRecipeReactiveRepository extends ReactiveMongoRepository<Recipe,String> {
}
