package ru.sandwichcloud.email;

import lombok.Data;

import java.util.List;

@Data
public class Sandwich {
    private final String name;
    private List<String> ingredients;
}
