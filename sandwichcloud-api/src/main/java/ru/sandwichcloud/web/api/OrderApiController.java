package ru.sandwichcloud.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sandwichcloud.SandwichOrder;
import ru.sandwichcloud.data.OrderRepository;

@RestController
@RequestMapping(path="api/orders", produces="application/json")
@CrossOrigin(origins="http://sandwichcloud:8080")
public class OrderApiController {
    private OrderRepository orderRepository;

    public OrderApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PutMapping(path="/{orderId}", consumes = "application/json")
    public SandwichOrder putOrder(@PathVariable("orderId") Long orderId,
                                  @RequestBody SandwichOrder order) {
        order.setId(orderId);

        return orderRepository.save(order);
    }

    @PatchMapping(path="/{orderId}", consumes = "application/json")
    public SandwichOrder patchOrder(@PathVariable("orderId") Long orderId,
                                    @RequestBody SandwichOrder patch) {

        SandwichOrder order = orderRepository.findById(orderId).get();

        if (patch.getDeliveryName() != null) {
            order.setDeliveryName(patch.getDeliveryName());
        }

        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliverySubject() != null) {
            order.setDeliverySubject(patch.getDeliverySubject());
        }
        if (patch.getCcNumber() != null) {
            order.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            order.setCcCVV(patch.getCcCVV());
        }

        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            //none
        }
    }



}
