package ru.sandwichcloud;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix="sandwich.discount")
@Data
public class DiscountCodeProps {

  private Map<String, Integer> codes = new HashMap<>();
  
}
