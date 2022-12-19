package ru.sandwichcloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.sandwichcloud.data.IngredientRepository;
import ru.sandwichcloud.data.SandwichRepository;
import ru.sandwichcloud.data.UserRepository;

import java.util.Arrays;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo,
                                      UserRepository userRepo,
                                      PasswordEncoder encoder,
                                      SandwichRepository sandwichRepo) {
    return args -> {
      repo.deleteAll();
      userRepo.deleteAll();

      Ingredient darkBread = new Ingredient("DARK", "Dark Bread", Ingredient.Type.BREAD);
      Ingredient whiteBread = new Ingredient("WHTE", "White Bread", Ingredient.Type.BREAD);
      Ingredient beef = new Ingredient("BEEF", "Beef", Ingredient.Type.PROTEIN);
      Ingredient pork = new Ingredient("PORK", "Pork", Ingredient.Type.PROTEIN);
      Ingredient tomatoes = new Ingredient("TMTO", "Tomatoes", Ingredient.Type.VEGGIES);
      Ingredient cucumber = new Ingredient("CMBR", "Cucumber", Ingredient.Type.VEGGIES);
      Ingredient cheddar = new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE);
      Ingredient maasdam = new Ingredient("MSDM", "Maasdam", Ingredient.Type.CHEESE);
      Ingredient mayonnaise = new Ingredient("MAYO", "Mayonnaise", Ingredient.Type.SAUCE);
      Ingredient mustard = new Ingredient("MTRD", "Mustard", Ingredient.Type.SAUCE);

      repo.save(darkBread);
      repo.save(whiteBread);
      repo.save(beef);
      repo.save(pork);
      repo.save(tomatoes);
      repo.save(cucumber);
      repo.save(cheddar);
      repo.save(maasdam);
      repo.save(mayonnaise);
      repo.save(mustard);

      userRepo.save(new User("testuser", encoder.encode("password"),
          "Test Testov", "Street 123", "Testgrad", "Oblast",
           "123-123-1234"));

      Sandwich sandwich1 = new Sandwich();
      sandwich1.setName("sandwich1");
      sandwich1.setIngredients(Arrays.asList(darkBread, beef, tomatoes, mayonnaise, cheddar));
      sandwichRepo.save(sandwich1);

      Sandwich sandwich2 = new Sandwich();
      sandwich2.setName("sandwich2");
      sandwich2.setIngredients(Arrays.asList(whiteBread, pork, cucumber, mustard, maasdam));
      sandwichRepo.save(sandwich2);

      Sandwich sandwich3 = new Sandwich();
      sandwich3.setName("sandwich3");
      sandwich3.setIngredients(Arrays.asList(whiteBread, darkBread, beef, pork, tomatoes, cucumber, mayonnaise, mustard, cheddar, maasdam));
      sandwichRepo.save(sandwich3);

    };
  }
  
}
