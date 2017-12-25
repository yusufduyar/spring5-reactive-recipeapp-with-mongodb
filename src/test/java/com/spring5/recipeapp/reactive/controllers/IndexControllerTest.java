package com.spring5.recipeapp.reactive.controllers;

import com.spring5.recipeapp.reactive.domain.Recipe;
import com.spring5.recipeapp.reactive.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    IndexController indexController;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() throws Exception {

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe secondRecipe = new Recipe();
        secondRecipe.setId("1");
        recipes.add(secondRecipe);

        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(),secondRecipe));

        ArgumentCaptor<Flux<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Flux.class);

        //when
        String viewName = indexController.getIndexPage(model);

        //then
        assertEquals(viewName, "index");
        Mockito.verify(recipeService, times(1)).getRecipes();
        Mockito.verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Flux<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.collectList().block().size());
    }

}