package ru.sandwichcloud.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.sandwichcloud.SandwichOrder;

@Profile("kafka")
@Service
public class KafkaOrderMessagingService implements OrderMessagingService {

    private final KafkaTemplate<String, SandwichOrder> kafkaTemplate;
    private final NewTopic topic;

    public KafkaOrderMessagingService(KafkaTemplate<String, SandwichOrder> kafkaTemplate, NewTopic topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void sendOrder(SandwichOrder order) {
        Message<SandwichOrder> message = MessageBuilder
                .withPayload(order)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);

        //kafkaTemplate.send("sandwichcloud.orders.topic", order);
    }
}
