package ru.sandwichcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.client.Hop.rel;

@Service
@Slf4j
public class SandwichCloudClient {

    private RestTemplate rest;

    private final String ingredientUrl = "http://localhost:8080/api/ingredients";

    public SandwichCloudClient(RestTemplate rest) {
        this.rest = rest;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject( ingredientUrl + "/{id}",
                Ingredient.class, ingredientId);
    }

    public List<Ingredient> getAllIngredients() {
        return rest.exchange(ingredientUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {})
                .getBody();
    }

    public void updateIngredient(Ingredient ingredient) {
        rest.put(ingredientUrl + "/{id}",
                ingredient, ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return rest.postForObject(ingredientUrl,
                ingredient, Ingredient.class);
    }

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete(ingredientUrl + "/{id}",
                ingredient.getId());
    }
}
