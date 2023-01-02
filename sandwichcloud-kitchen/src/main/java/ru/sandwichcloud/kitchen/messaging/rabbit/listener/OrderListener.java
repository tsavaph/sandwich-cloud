package ru.sandwichcloud.kitchen.messaging.rabbit.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.sandwichcloud.domain.SandwichOrder;
import ru.sandwichcloud.kitchen.KitchenUI;

@Profile("rabbit")
@Component
public class OrderListener {

    private KitchenUI kitchenUI;

    @Autowired
    public OrderListener(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
    }

    @RabbitListener(queues = "sandwichcloud.order")
    public void receiveOrder(SandwichOrder sandwichOrder) {
        kitchenUI.displayOrder(sandwichOrder);
    }
}
