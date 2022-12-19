package ru.sandwichcloud;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.rest.core.annotation.RestResource;

import lombok.Data;

@Data
@Entity
@RestResource(rel="sandwiches", path="sandwiches")
public class Sandwich {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  
  @NotNull
  @Size(min=5, message="Name must be at least 5 characters long")
  private String name;
  
  private Date createdAt;

  @ManyToMany(targetEntity=Ingredient.class)
  @Size(min=1, message="You must choose at least 1 ingredient")
  private List<Ingredient> ingredients = new ArrayList<>();

  @PrePersist
  void createdAt() {
    this.createdAt = new Date();
  }
  
  public void addIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient);
  }
}
