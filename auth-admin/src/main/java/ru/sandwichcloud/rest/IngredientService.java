package ru.sandwichcloud.rest;

import ru.sandwichcloud.domain.Ingredient;

public interface IngredientService {
    Iterable<Ingredient> findAll();
    Ingredient addIngredient(Ingredient ingredient);
}
