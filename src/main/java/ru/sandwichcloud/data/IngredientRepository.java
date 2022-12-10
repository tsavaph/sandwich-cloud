package ru.sandwichcloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.sandwichcloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
