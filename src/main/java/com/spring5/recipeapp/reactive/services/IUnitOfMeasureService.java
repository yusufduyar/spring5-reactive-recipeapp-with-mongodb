package com.spring5.recipeapp.reactive.services;

import com.spring5.recipeapp.reactive.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface IUnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
