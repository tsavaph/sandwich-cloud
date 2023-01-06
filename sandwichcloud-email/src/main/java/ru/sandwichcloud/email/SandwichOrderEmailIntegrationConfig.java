package ru.sandwichcloud.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class SandwichOrderEmailIntegrationConfig {

    @Bean
    IntegrationFlow sandwichOrderEmailFlow(
            EmailProperties emailProperties,
            EmailToOrderTransformer emailToOrderTransformer,
            OrderSubmitMessageHandler orderSubmitMessageHandler) {

        return IntegrationFlow
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl())
                                .simpleContent(true),
                        e -> e.poller(
                                Pollers.fixedDelay(emailProperties.getPollRate())))
                .transform(emailToOrderTransformer)
                .handle(orderSubmitMessageHandler)
                .get();
    }
}
