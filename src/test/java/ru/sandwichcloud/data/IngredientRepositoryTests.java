package ru.sandwichcloud.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.sandwichcloud.Ingredient;
import ru.sandwichcloud.Ingredient.Type;

@SpringBootTest
public class IngredientRepositoryTests {

  @Autowired
  IngredientRepository ingredientRepo;
  
  @Test
  public void findById() {
    Optional<Ingredient> dark = ingredientRepo.findById("DARK");
    assertThat(dark.isPresent()).isTrue();
    assertThat(dark.get()).isEqualTo(new Ingredient("DARK", "Dark Bread", Type.BREAD));
    
    Optional<Ingredient> xxxx = ingredientRepo.findById("XXXX");
    assertThat(xxxx.isEmpty()).isTrue();

  }
  
}
