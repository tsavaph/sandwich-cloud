package ru.sandwichcloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.sandwichcloud.data.IngredientRepository;
import ru.sandwichcloud.data.UserRepository;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo,
                                      UserRepository userRepo,
                                      PasswordEncoder encoder) { // user repo for ease of testing with a built-in user
    return args -> {
      repo.deleteAll();
      userRepo.deleteAll();

      repo.save(new Ingredient("DARK", "Dark Bread", Ingredient.Type.BREAD));
      repo.save(new Ingredient("WHTE", "White Bread", Ingredient.Type.BREAD));
      repo.save(new Ingredient("BEEF", "Beef", Ingredient.Type.PROTEIN));
      repo.save(new Ingredient("PORK", "Pork", Ingredient.Type.PROTEIN));
      repo.save(new Ingredient("TMTO", "Tomatoes", Ingredient.Type.VEGGIES));
      repo.save(new Ingredient("CMBR", "Cucumber", Ingredient.Type.VEGGIES));
      repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
      repo.save(new Ingredient("MSDM", "Maasdam", Ingredient.Type.CHEESE));
      repo.save(new Ingredient("MAYO", "Mayonnaise", Ingredient.Type.SAUCE));
      repo.save(new Ingredient("MTRD", "Mustard", Ingredient.Type.SAUCE));

      userRepo.save(new User("testuser", encoder.encode("password"),
          "Test Testov", "Street 123", "Testgrad", "Oblast",
           "123-123-1234"));
    };
  }
  
}
