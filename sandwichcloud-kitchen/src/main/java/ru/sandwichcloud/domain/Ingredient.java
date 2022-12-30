package ru.sandwichcloud.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class Ingredient {

  private final String name;
  private final Type type;

  public enum Type {
    BREAD, PROTEIN, VEGGIES, CHEESE, SAUCE
  }

}
