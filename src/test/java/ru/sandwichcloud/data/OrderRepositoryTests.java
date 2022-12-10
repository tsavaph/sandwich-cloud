package ru.sandwichcloud.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.sandwichcloud.SandwichOrder;
import ru.sandwichcloud.Sandwich;
import ru.sandwichcloud.Ingredient;
import ru.sandwichcloud.Ingredient.Type;

import java.util.List;

@DataJpaTest
public class OrderRepositoryTests {

  @Autowired
  OrderRepository orderRepo;
  
  @Test
  public void saveOrderWithTwoSandwiches() {
    SandwichOrder order = new SandwichOrder();
    order.setDeliveryName("Test Testov");
    order.setDeliveryStreet("1234 Test Street");
    order.setDeliveryCity("Testgrad");
    order.setDeliverySubject("Oblast");
    order.setCcNumber("4111111111111111");
    order.setCcExpiration("10/25");
    order.setCcCVV("123");
    Sandwich sandwich1 = new Sandwich();
    sandwich1.setName("Sandwich One");
    sandwich1.addIngredient(new Ingredient("DARK", "Dark Bread", Type.BREAD));
    sandwich1.addIngredient(new Ingredient("BEEF", "Beef", Type.PROTEIN));
    sandwich1.addIngredient(new Ingredient("CHED", "Cheddar", Type.CHEESE));
    order.addSandwich(sandwich1);
    Sandwich sandwich2 = new Sandwich();
    sandwich2.setName("Sandwich Two");
    sandwich2.addIngredient(new Ingredient("WHTE", "White Bread", Type.BREAD));
    sandwich2.addIngredient(new Ingredient("PORK", "Pork", Type.PROTEIN));
    sandwich2.addIngredient(new Ingredient("MSDM", "Maasdam", Type.CHEESE));
    order.addSandwich(sandwich2);
    
    SandwichOrder savedOrder = orderRepo.save(order);
    assertThat(savedOrder.getId()).isNotNull();
        
    SandwichOrder fetchedOrder = orderRepo.findById(savedOrder.getId()).get();
    assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test Testov");
    assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Street");
    assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testgrad");
    assertThat(fetchedOrder.getDeliverySubject()).isEqualTo("Oblast");
    assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
    assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/25");
    assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
    assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(savedOrder.getPlacedAt().getTime());
    List<Sandwich> sandwiches = fetchedOrder.getSandwiches();
    assertThat(sandwiches.size()).isEqualTo(2);
    assertThat(sandwiches).containsExactlyInAnyOrder(sandwich1, sandwich2);
  }
  
}
