package ru.sandwichcloud.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sandwichcloud.Ingredient;
import ru.sandwichcloud.Ingredient.Type;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put("DARK", new Ingredient("DARK", "Dark Bread", Type.BREAD));
        ingredientMap.put("WHTE", new Ingredient("WHTE", "White Bread", Type.BREAD));
        ingredientMap.put("BEEF", new Ingredient("BEEF", "Beef", Type.PROTEIN));
        ingredientMap.put("PORK", new Ingredient("PORK", "Pork", Type.PROTEIN));
        ingredientMap.put("TMTO", new Ingredient("TMTO", "Tomatoes", Type.VEGGIES));
        ingredientMap.put("CMBR", new Ingredient("CMBR", "Cucumber", Type.VEGGIES));
        ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredientMap.put("MSDM", new Ingredient("MSDM", "Maasdam", Type.CHEESE));
        ingredientMap.put("MAYO", new Ingredient("MAYO", "Mayonnaise", Type.SAUCE));
        ingredientMap.put("MTRD", new Ingredient("MTRD", "Mustard", Type.SAUCE));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
