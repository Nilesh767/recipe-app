package com.n3o.recipeapp.services;

import com.n3o.recipeapp.commands.RecipeCommand;
import com.n3o.recipeapp.converters.RecipeCommandToRecipe;
import com.n3o.recipeapp.converters.RecipeToRecipeCommand;
import com.n3o.recipeapp.domain.Recipe;
import com.n3o.recipeapp.exceptions.NotFoundException;
import com.n3o.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        log.debug("you hu... i am called... recipe service boi!!!");
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long aLong) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(aLong);
        if (!recipeOptional.isPresent()) {
            //throw new RuntimeException("Recipe Not Found");
            throw new NotFoundException("Recipe not Found");
        }
        return recipeOptional.get();
    }

    @Override
    public void deleteById(Long aLong) {
        recipeRepository.deleteById(aLong);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long aLong) {
        return recipeToRecipeCommand.convert(findById(aLong));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("saved RecipeID: "+savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }
}
