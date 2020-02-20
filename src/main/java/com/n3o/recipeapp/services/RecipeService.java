package com.n3o.recipeapp.services;

import com.n3o.recipeapp.commands.RecipeCommand;
import com.n3o.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long aLong);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
