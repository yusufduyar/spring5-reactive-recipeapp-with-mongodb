package com.spring5.recipeapp.reactive.repositories.reactive;

import com.spring5.recipeapp.reactive.domain.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IIngredientReactiveRepository extends ReactiveMongoRepository<Ingredient, String> {
}
