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

    @Bean
    public CommandLineRunner fetchIngredients(SandwichCloudClient sandwichCloudClient) {
        return args -> {
            log.info("----------------------- GET -------------------------");
            log.info("GETTING INGREDIENT BY IDE");
            log.info("Ingredient:  " + sandwichCloudClient.getIngredientById("CHED"));
            log.info("GETTING ALL INGREDIENTS");
            List<Ingredient> ingredients = sandwichCloudClient.getAllIngredients();
            log.info("All ingredients:");
            for (Ingredient ingredient : ingredients) {
                log.info("   - " + ingredient);
            }
        };
    }

    @Bean
    public CommandLineRunner putAnIngredient(SandwichCloudClient sandwichCloudClient) {
        return args -> {
            log.info("----------------------- PUT -------------------------");
            Ingredient before = sandwichCloudClient.getIngredientById("TMTO");
            log.info("BEFORE:  " + before);
            sandwichCloudClient.updateIngredient(new Ingredient("TMTO", "Shredded Tomatoes", Ingredient.Type.VEGGIES));
            Ingredient after = sandwichCloudClient.getIngredientById("TMTO");
            log.info("AFTER:  " + after);
        };
    }

    @Bean
    public CommandLineRunner addAnIngredient(SandwichCloudClient sandwichCloudClient) {
        return args -> {
            log.info("----------------------- POST -------------------------");
            Ingredient chix = new Ingredient("CHIX", "Shredded Chicken", Ingredient.Type.PROTEIN);
            Ingredient chixAfter = sandwichCloudClient.createIngredient(chix);
            log.info("AFTER=1:  " + chixAfter);
        };
    }


    @Bean
    public CommandLineRunner deleteAnIngredient(SandwichCloudClient sandwichCloudClient) {
        return args -> {
            log.info("----------------------- DELETE -------------------------");
            // start by adding a few ingredients so that we can delete them later...
            Ingredient beefFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
            sandwichCloudClient.createIngredient(beefFajita);
            Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
            sandwichCloudClient.createIngredient(shrimp);


            Ingredient before = sandwichCloudClient.getIngredientById("CHIX");
            log.info("BEFORE:  " + before);
            sandwichCloudClient.deleteIngredient(before);

            log.info("All ingredients after deleting: " + before);
            List<Ingredient> ingredients = sandwichCloudClient.getAllIngredients();
            for (Ingredient ingredient : ingredients) {
                log.info("   - " + ingredient);
            }

            before = sandwichCloudClient.getIngredientById("BFFJ");
            log.info("BEFORE:  " + before);
            sandwichCloudClient.deleteIngredient(before);

            log.info("All ingredients after deleting: " + before);
            ingredients = sandwichCloudClient.getAllIngredients();
            for (Ingredient ingredient : ingredients) {
                log.info("   - " + ingredient);
            }

            before = sandwichCloudClient.getIngredientById("SHMP");
            log.info("BEFORE:  " + before);
            sandwichCloudClient.deleteIngredient(before);

            log.info("All ingredients after deleting: " + before);
            ingredients = sandwichCloudClient.getAllIngredients();
            for (Ingredient ingredient : ingredients) {
                log.info("   - " + ingredient);
            }
        };
    }
}
