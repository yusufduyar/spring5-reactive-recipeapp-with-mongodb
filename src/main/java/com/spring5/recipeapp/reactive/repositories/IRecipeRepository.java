package com.spring5.recipeapp.reactive.repositories;

import com.spring5.recipeapp.reactive.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface IRecipeRepository extends CrudRepository<Recipe,String> {
}
