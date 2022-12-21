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
    private Traverson traverson;

    public SandwichCloudClient(RestTemplate rest, Traverson traverson) {
        this.rest = rest;
        this.traverson = traverson;
    }


    //
    // Traverson with RestTemplate examples
    //

    public Iterable<Ingredient> getAllIngredientsWithTraverson() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};

        CollectionModel<Ingredient> ingredientRes =
                traverson
                        .follow("ingredients")
                        .toObject(ingredientType);

        Collection<Ingredient> ingredients = ingredientRes.getContent();
        return ingredients;
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        String ingredientsUrl = traverson
                .follow("ingredients")
                .asLink()
                .getHref();

        return rest.postForObject(ingredientsUrl,
                ingredient,
                Ingredient.class);
    }



    public Iterable<Sandwich> getRecentSandwichesWithTraverson() {
        ParameterizedTypeReference<CollectionModel<Sandwich>> sandwichType =
                new ParameterizedTypeReference<CollectionModel<Sandwich>>() {};

        CollectionModel<Sandwich> sandwichRes =
                traverson
                        .follow(rel("sandwiches").withParameter("recent", 0))
                        .toObject(sandwichType);

        Collection<Sandwich> sandwiches = sandwichRes.getContent();
        return sandwiches;
    }




}
