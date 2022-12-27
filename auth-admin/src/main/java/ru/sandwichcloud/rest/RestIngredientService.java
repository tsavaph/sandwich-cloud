package ru.sandwichcloud.rest;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import ru.sandwichcloud.domain.Ingredient;

import java.util.Arrays;

public class RestIngredientService implements IngredientService {

    private RestTemplate restTemplate;
    private final String link = "http://localhost:8080/api/ingredients";

    public RestIngredientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return Arrays.asList(restTemplate.getForObject(
                link,
                Ingredient[].class
        ));
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return restTemplate.postForObject(
                link,
                ingredient,
                Ingredient.class
        );
    }

    public RestIngredientService(String accessToken) {
        this.restTemplate = new RestTemplate();
        if (accessToken != null) {
            this.restTemplate
                    .getInterceptors()
                    .add(getBearerTokenInterceptor(accessToken));
        }
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        ClientHttpRequestInterceptor clientHttpRequestInterceptor = (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        };
        return clientHttpRequestInterceptor;
    }
}
