package ru.sandwichcloud.email;

import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {

    private RestTemplate restTemplate;
    private ApiProperties apiProperties;

    public OrderSubmitMessageHandler(RestTemplate restTemplate, ApiProperties apiProperties) {
        this.apiProperties = apiProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Object handle(EmailOrder payload, MessageHeaders headers) {
        restTemplate.postForObject(apiProperties.getUrl(), payload.toString(), String.class);
        return null;
    }
}
