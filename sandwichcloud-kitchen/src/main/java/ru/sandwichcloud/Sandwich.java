package ru.sandwichcloud;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Sandwich {

  private String name;
  private Date createdAt;
  private List<Ingredient> ingredients = new ArrayList<>();
}
