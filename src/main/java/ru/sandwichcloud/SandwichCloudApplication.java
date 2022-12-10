package ru.sandwichcloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.sandwichcloud.data.IngredientRepository;
import ru.sandwichcloud.Ingredient.Type;

@SpringBootApplication
public class SandwichCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SandwichCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) {
		return args -> {
			repo.save(new Ingredient("DARK", "Dark Bread", Type.BREAD));
			repo.save(new Ingredient("WHTE", "White Bread", Type.BREAD));
			repo.save(new Ingredient("BEEF", "Beef", Type.PROTEIN));
			repo.save(new Ingredient("PORK", "Pork", Type.PROTEIN));
			repo.save(new Ingredient("TMTO", "Tomatoes", Type.VEGGIES));
			repo.save(new Ingredient("CMBR", "Cucumber", Type.VEGGIES));
			repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
			repo.save(new Ingredient("MSDM", "Maasdam", Type.CHEESE));
			repo.save(new Ingredient("MAYO", "Mayonnaise", Type.SAUCE));
			repo.save(new Ingredient("MTRD", "Mustard", Type.SAUCE));
		};
	}
}
