package ru.sandwichcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@SpringBootConfiguration
@ComponentScan
@Slf4j
public class RestExamples {

    public static void main(String[] args) {
        SpringApplication.run(RestExamples.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //
    // Traverson examples
    //

    @Bean
    public Traverson traverson() {
        Traverson traverson = new Traverson(
                URI.create("http://localhost:8080/data-api"), MediaTypes.HAL_JSON);
        return traverson;
    }

    @Bean
    public CommandLineRunner traversonGetIngredients(SandwichCloudClient sandwichCloudClient) {
        return args -> {
            Iterable<Ingredient> ingredients = sandwichCloudClient.getAllIngredientsWithTraverson();
            log.info("----------------------- GET INGREDIENTS WITH TRAVERSON -------------------------");
            for (Ingredient ingredient : ingredients) {
                log.info("   -  " + ingredient);
            }
        };
    }

    @Bean
    public CommandLineRunner traversonSaveIngredient(SandwichCloudClient sandwichCloudClient) {
        return args -> {
            Ingredient pico = sandwichCloudClient.addIngredient(
                    new Ingredient("PICO", "Pico de Gallo", Ingredient.Type.SAUCE));
            Iterable<Ingredient> allIngredients = sandwichCloudClient.getAllIngredientsWithTraverson();
            log.info("----------------------- ALL INGREDIENTS AFTER SAVING PICO -------------------------");
            for (Ingredient ingredient : allIngredients) {
                log.info("   -  " + ingredient);
            }
        };
    }

    @Bean
    public CommandLineRunner traversonRecentSandwiches(SandwichCloudClient sandwichCloudClient) {
        return args -> {
            Iterable<Sandwich> recentSandwiches = sandwichCloudClient.getRecentSandwichesWithTraverson();
            log.info("----------------------- GET RECENT SANDWICHES WITH TRAVERSON -------------------------");
            for (Sandwich sandwich : recentSandwiches) {
                log.info("   -  " + sandwich);
            }
        };
    }
}
