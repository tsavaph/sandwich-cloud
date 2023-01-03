package ru.sandwichcloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import ru.sandwichcloud.domain.SandwichOrder;
import ru.sandwichcloud.kitchen.KitchenUI;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderReceiverController {

//  private final OrderReceiver orderReceiver;
//
//  @GetMapping("/receive")
//  public String receiveOrder(Model model) {
//    SandwichOrder order = orderReceiver.receiveOrder();
//    if (order != null) {
//      model.addAttribute("order", order);
//      return "receiveOrder";
//    }
//    return "noOrder";
//  }

  private final KitchenUI kitchenUI;

  @GetMapping("/receive")
  public String receiveOrder(Model model) {
    List<SandwichOrder> orders = kitchenUI.getOrders();
    if (!orders.isEmpty()) {
      model.addAttribute("order", orders.get(orders.size() - 1));
      return "receiveOrder";
    }
    return "noOrder";
  }

}
