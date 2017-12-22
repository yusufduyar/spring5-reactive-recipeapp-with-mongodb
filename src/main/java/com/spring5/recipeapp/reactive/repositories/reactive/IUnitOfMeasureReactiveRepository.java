package com.spring5.recipeapp.reactive.repositories.reactive;

import com.spring5.recipeapp.reactive.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IUnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure,String> {
    Mono<UnitOfMeasure> findByDescription(String description);
}
