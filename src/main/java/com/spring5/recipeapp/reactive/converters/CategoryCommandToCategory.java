package com.spring5.recipeapp.reactive.converters;

import com.spring5.recipeapp.reactive.commands.CategoryCommand;
import com.spring5.recipeapp.reactive.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        if(categoryCommand == null){
            return null;
        }
        final Category category = new Category();
        category.setId(categoryCommand.getId());
        category.setDescription(categoryCommand.getDescription());

        return category;
    }
}
