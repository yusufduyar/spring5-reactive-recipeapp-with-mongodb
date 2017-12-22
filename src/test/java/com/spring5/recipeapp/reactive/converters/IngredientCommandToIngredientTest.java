package com.spring5.recipeapp.reactive.converters;

import com.spring5.recipeapp.reactive.commands.IngredientCommand;
import com.spring5.recipeapp.reactive.commands.UnitOfMeasureCommand;
import com.spring5.recipeapp.reactive.domain.Ingredient;
import com.spring5.recipeapp.reactive.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    private static final Recipe recipe = new Recipe();
    private static final BigDecimal amount = new BigDecimal("1");
    private static final String description = "Cheeseburger";
    private static final String idValue = "1";
    private static final String unitOfMeasureId = "2";
    IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void when_command_object_is_null_converter_returns_null() {
        assertNull(converter.convert(null));
    }

    @Test
    public void when_command_object_is_not_null_but_empty_converter_returns_empty() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(unitOfMeasureId);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(idValue);
        ingredientCommand.setAmount(amount);
        ingredientCommand.setDescription(description);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        Ingredient ingredient = converter.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(idValue,ingredient.getId());
        assertEquals(description,ingredient.getDescription());
        assertEquals(amount,ingredient.getAmount());
        assertEquals(unitOfMeasureId,ingredient.getUnitOfMeasure().getId());
    }

    @Test
    public void when_unitOfMeasure_of_ingredientCommand_is_null_converter_sets_it_to_null(){
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(idValue);
        command.setAmount(amount);
        command.setDescription(description);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();

        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(idValue, ingredient.getId());
        assertEquals(amount, ingredient.getAmount());
        assertEquals(description, ingredient.getDescription());
    }

}