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
import ru.sandwichcloud.User;
import ru.sandwichcloud.data.IngredientRepository;
import ru.sandwichcloud.data.SandwichRepository;
import ru.sandwichcloud.data.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("sandwichOrder")
public class DesignSandwichController {

    private final IngredientRepository ingredientRepo;

    private UserRepository userRepo;

    private SandwichRepository sandwichRepo;

    @Autowired
    public DesignSandwichController(IngredientRepository ingredientRepo,
                                    UserRepository userRepo,
                                    SandwichRepository sandwichRepo) {
        this.ingredientRepo = ingredientRepo;
        this.sandwichRepo = sandwichRepo;
        this.userRepo = userRepo;
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

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        return user;
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

        Sandwich saved = sandwichRepo.save(sandwich);
        sandwichOrder.addSandwich(saved);

        return "redirect:/orders/current";
    }


    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
