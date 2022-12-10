package ru.sandwichcloud.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.sandwichcloud.Ingredient;
import ru.sandwichcloud.Ingredient.Type;
import ru.sandwichcloud.Sandwich;
import ru.sandwichcloud.SandwichOrder;
import ru.sandwichcloud.data.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("sandwichOrder")
public class DesignSandwichController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignSandwichController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "sandwichOrder")
    public SandwichOrder order() {
        return new SandwichOrder();
    }

    @ModelAttribute(name = "sandwich")
    public Sandwich sandwich() {
        return new Sandwich();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processSandwich(@Valid Sandwich sandwich,
                                  Errors errors,
                                  @ModelAttribute SandwichOrder sandwichOrder) {

        if (errors.hasErrors()) {
            return "design";
        }
        sandwichOrder.addSandwich(sandwich);
        log.info("Processing sandwich: {}", sandwich);

        return "redirect:/orders/current";
    }


    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
