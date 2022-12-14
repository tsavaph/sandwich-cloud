package ru.sandwichcloud.kitchen.messaging.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.sandwichcloud.SandwichOrder;
import ru.sandwichcloud.kitchen.messaging.OrderReceiver;

@Profile("rabbit")
@Component
public class RabbitOrderReceiver implements OrderReceiver {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public SandwichOrder receiveOrder() {
        return (SandwichOrder) rabbitTemplate.receiveAndConvert("sandwichcloud.order");
    }


}
