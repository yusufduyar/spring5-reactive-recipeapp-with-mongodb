package com.spring5.recipeapp.reactive.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId;
    @NotBlank
    private String description;
    @Min(1)
    @NotNull
    private BigDecimal amount;
    @NotNull
    private UnitOfMeasureCommand unitOfMeasure;

}