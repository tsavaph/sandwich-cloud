package ru.sandwichcloud.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.sandwichcloud.Ingredient;
import ru.sandwichcloud.Ingredient.Type;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class DesignSandwichControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private List<Ingredient> ingredients;

    @BeforeEach
    public void setup() {
        ingredients = Arrays.asList(
                new Ingredient("DARK", "Dark Bread", Type.BREAD),
                new Ingredient("WHTE", "White Bread", Type.BREAD),
                new Ingredient("BEEF", "Beef", Type.PROTEIN),
                new Ingredient("PORK", "Pork", Type.PROTEIN),
                new Ingredient("TMTO", "Tomatoes", Type.VEGGIES),
                new Ingredient("CMBR", "Cucumber", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("MSDM", "Maasdam", Type.CHEESE),
                new Ingredient("MAYO", "Mayonnaise", Type.SAUCE),
                new Ingredient("MTRD", "Mustard", Type.SAUCE)
        );
    }

    @Test
    public void testShowDesignForm() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("bread", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
                .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
                .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
    }

    @Test
    public void testProcessSandwich() throws Exception {
        mockMvc.perform(post("/design")
                .content("name=Test+Sandwich&ingredients=DARK,BEEF,CHED")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/orders/current"));
    }

}
