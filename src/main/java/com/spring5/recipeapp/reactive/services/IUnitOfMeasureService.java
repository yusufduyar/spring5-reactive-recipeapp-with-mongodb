package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface IUnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
