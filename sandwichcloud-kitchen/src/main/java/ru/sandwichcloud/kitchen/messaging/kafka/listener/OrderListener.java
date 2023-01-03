package ru.sandwichcloud.kitchen.messaging.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.sandwichcloud.SandwichOrder;
import ru.sandwichcloud.kitchen.KitchenUI;

@Component
@Slf4j
@Profile("kafka")
public class OrderListener {
    private KitchenUI kitchenUI;

    public OrderListener(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
    }

    @KafkaListener(topics = "sandwichcloud.orders.topic", groupId = "${spring.kafka.consumer.group-id}")
    public void handle(SandwichOrder sandwichOrder, ConsumerRecord<String, SandwichOrder> record) {
        log.info("Received from partition {} with timestamp {}",
                record.partition(),
                record.timestamp());

        kitchenUI.displayOrder(sandwichOrder);
    }
}
