package ru.sandwichcloud.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.sandwichcloud.SandwichOrder;
import ru.sandwichcloud.User;
import ru.sandwichcloud.data.OrderRepository;
import ru.sandwichcloud.messaging.OrderMessagingService;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("sandwichOrder")
public class OrderController {

    private OrderRepository orderRepo;
    private OrderProps orderProps;
    private OrderMessagingService orderMessagingService;

    public OrderController(OrderRepository orderRepo,
                           OrderProps orderProps,
                           OrderMessagingService orderMessagingService) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
        this.orderMessagingService = orderMessagingService;
    }
    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute SandwichOrder order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliverySubject() == null) {
            order.setDeliverySubject(user.getSubject());
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid SandwichOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);
        SandwichOrder messagingOrder = orderRepo.save(order);
        orderMessagingService.sendOrder(messagingOrder);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user,
                                Model model) {
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

}
