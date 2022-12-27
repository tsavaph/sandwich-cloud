package ru.sandwichcloud.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sandwichcloud.Ingredient;
import ru.sandwichcloud.data.IngredientRepository;

@RestController
@RequestMapping(path="api/ingredients", produces="application/json")
@CrossOrigin(origins="http://sandwichcloud:8080")
public class IngredientController {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ingredient getIngredientById(@PathVariable("id") String ingredientId) {
        return ingredientRepository.findById(ingredientId).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") String ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @PutMapping(path="/{Id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Ingredient updateIngredient(@PathVariable("Id") String ingredientId, @RequestBody Ingredient ingredient) {

        return ingredientRepository.save(ingredient);
    }

}
