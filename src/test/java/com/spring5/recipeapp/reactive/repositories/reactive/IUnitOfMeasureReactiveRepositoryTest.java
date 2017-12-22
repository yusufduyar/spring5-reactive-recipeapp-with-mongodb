package com.spring5.recipeapp.reactive.repositories.reactive;

import com.spring5.recipeapp.reactive.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class IUnitOfMeasureReactiveRepositoryTest {

    @Autowired
    IUnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }


    @Test
    public void saveUnitOfMeasureTest() throws Exception{
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Each");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        Long count = unitOfMeasureReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L),count);
    }
    @Test
    public void findByDescription() throws Exception {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Each");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure unitOfMeasureSelected = unitOfMeasureReactiveRepository.findByDescription("Each").block();

        assertEquals("Each",unitOfMeasureSelected.getDescription());
    }

}