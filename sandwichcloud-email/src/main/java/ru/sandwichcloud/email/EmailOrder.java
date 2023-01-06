package ru.sandwichcloud.email;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailOrder {
    private final String email;
    private List<Sandwich> sandwiches = new ArrayList<>();

    public void addSandwich(Sandwich sandwich) {
        this.sandwiches.add(sandwich);
    }
}
