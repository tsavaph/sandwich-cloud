package ru.sandwichcloud;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SandwichOrder implements Serializable {

  private Date placedAt;
  private String deliveryName;
  private String deliveryStreet;
  private String deliveryCity;
  private String deliverySubject;

  private List<Sandwich> sandwiches = new ArrayList<>();

}
