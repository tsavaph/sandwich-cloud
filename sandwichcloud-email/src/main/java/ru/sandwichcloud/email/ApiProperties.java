package ru.sandwichcloud.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "sandwichcloud.api")
@Component
public class ApiProperties {
    private String url;
}
