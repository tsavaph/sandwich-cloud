package ru.sandwichcloud.kitchen;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.sandwichcloud.domain.SandwichOrder;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class KitchenUI {
  // just for testing, no DB
  private final List<SandwichOrder> orders = new ArrayList<>();

  public void displayOrder(SandwichOrder order) {
    orders.add(order);
    log.info("RECEIVED ORDER:  " + order);
  }

  public List<SandwichOrder> getOrders() {
    return orders;
  }



  
}
