package com.spring5.recipeapp.reactive.repositories;

import com.spring5.recipeapp.reactive.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IIngredientRepository extends CrudRepository<Ingredient, String> {
}
