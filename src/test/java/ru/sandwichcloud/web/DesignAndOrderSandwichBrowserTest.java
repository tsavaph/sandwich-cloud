package ru.sandwichcloud.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DesignAndOrderSandwichBrowserTest {

    private static HtmlUnitDriver browser;

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate rest;

    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void testDesignASandwichPage_HappyPath() {
        browser.get(homePageUrl());
        clickDesignASandwich();
        assertLandedOnLoginPage();
        doRegistration("testuser", "testpassword");
        assertLandedOnLoginPage();
        doLogin("testuser", "testpassword");
        assertDesignPageElements();
        buildAndSubmitASandwich("Basic Sandwich", "DARK", "BEEF", "CHED", "TMTO", "MAYO");
        clickBuildAnotherSandwich();
        buildAndSubmitASandwich("Another Sandwich", "WHTE", "PORK", "MSDM", "CMBR", "MTRD");
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    @Test
    public void testDesignASandwichPage_EmptyOrderInfo() {
        browser.get(homePageUrl());
        clickDesignASandwich();
        assertLandedOnLoginPage();
        doRegistration("testuser2", "testpassword");
        doLogin("testuser2", "testpassword");
        assertDesignPageElements();
        buildAndSubmitASandwich("Basic Sandwich", "DARK", "BEEF", "CHED", "TMTO", "MAYO");
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    @Test
    public void testDesignASandwichPage_InvalidOrderInfo() {
        browser.get(homePageUrl());
        clickDesignASandwich();
        assertLandedOnLoginPage();
        doRegistration("testuser3", "testpassword");
        assertLandedOnLoginPage();
        doLogin("testuser3", "testpassword");
        assertDesignPageElements();
        buildAndSubmitASandwich("Basic Sandwich", "DARK", "BEEF", "CHED", "TMTO", "MAYO");
        submitInvalidOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    //
    // Browser test action methods
    //
    private void buildAndSubmitASandwich(String name, String... ingredients) {
        assertDesignPageElements();

        for (String ingredient : ingredients) {
            browser.findElement(By.cssSelector("input[value='" + ingredient + "']")).click();
        }
        browser.findElement(By.cssSelector("input#name")).sendKeys(name);
        browser.findElement(By.cssSelector("form#sandwichForm")).submit();
    }

    private void assertLandedOnLoginPage() {
        assertThat(browser.getCurrentUrl()).isEqualTo(loginPageUrl());
    }

    private void doRegistration(String username, String password) {
        browser.findElement(By.linkText("here")).click();
        assertThat(browser.getCurrentUrl()).isEqualTo(registrationPageUrl());
        browser.findElement(By.name("username")).sendKeys(username);
        browser.findElement(By.name("password")).sendKeys(password);
        browser.findElement(By.name("confirm")).sendKeys(password);
        browser.findElement(By.name("fullname")).sendKeys("Test Testov");
        browser.findElement(By.name("street")).sendKeys("1234 Test Street");
        browser.findElement(By.name("city")).sendKeys("Testgrad");
        browser.findElement(By.name("subject")).sendKeys("Oblast");
        browser.findElement(By.name("phone")).sendKeys("123-123-1234");
        browser.findElement(By.cssSelector("form#registerForm")).submit();
    }

    private void doLogin(String username, String password) {
        browser.findElement(By.cssSelector("input#username")).sendKeys(username);
        browser.findElement(By.cssSelector("input#password")).sendKeys(password);
        browser.findElement(By.cssSelector("form#loginForm")).submit();
    }

    private void doLogout() {
        WebElement logoutForm = browser.findElement(By.cssSelector("form#logoutForm"));

        if (logoutForm != null) {
            logoutForm.submit();
        }
    }

    private void assertDesignPageElements() {
        assertThat(browser.getCurrentUrl()).isEqualTo(designPageUrl() + "?continue");
        List<WebElement> ingredientGroups = browser.findElements(By.className("ingredient-group"));
        assertThat(ingredientGroups.size()).isEqualTo(5);

        WebElement breadGroup = browser.findElement(By.cssSelector("div.ingredient-group#breads"));
        List<WebElement> breads = breadGroup.findElements(By.tagName("div"));
        assertThat(breads.size()).isEqualTo(2);
        assertIngredient(breadGroup, 0, "DARK", "Dark Bread");
        assertIngredient(breadGroup, 1, "WHTE", "White Bread");

        WebElement proteinGroup = browser.findElement(By.cssSelector("div.ingredient-group#proteins"));
        List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
        assertThat(proteins.size()).isEqualTo(2);
        assertIngredient(proteinGroup, 0, "BEEF", "Beef");
        assertIngredient(proteinGroup, 1, "PORK", "Pork");

        WebElement cheeseGroup = browser.findElement(By.cssSelector("div.ingredient-group#cheeses"));
        List<WebElement> cheeses = proteinGroup.findElements(By.tagName("div"));
        assertThat(cheeses.size()).isEqualTo(2);
        assertIngredient(cheeseGroup, 0, "CHED", "Cheddar");
        assertIngredient(cheeseGroup, 1, "MSDM", "Maasdam");

        WebElement veggieGroup = browser.findElement(By.cssSelector("div.ingredient-group#veggies"));
        List<WebElement> veggies = proteinGroup.findElements(By.tagName("div"));
        assertThat(veggies.size()).isEqualTo(2);
        assertIngredient(veggieGroup, 0, "TMTO", "Tomatoes");
        assertIngredient(veggieGroup, 1, "CMBR", "Cucumber");

        WebElement sauceGroup = browser.findElement(By.cssSelector("div.ingredient-group#sauces"));
        List<WebElement> sauces = proteinGroup.findElements(By.tagName("div"));
        assertThat(sauces.size()).isEqualTo(2);
        assertIngredient(sauceGroup, 0, "MAYO", "Mayonnaise");
        assertIngredient(sauceGroup, 1, "MTRD", "Mustard");
    }


    private void fillInAndSubmitOrderForm() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        fillField("input#deliveryName", "Ima Hungry");
        fillField("input#deliveryStreet", "1234 Culinary Blvd.");
        fillField("input#deliveryCity", "Foodsville");
        fillField("input#deliverySubject", "CO");
        fillField("input#ccNumber", "4111111111111111");
        fillField("input#ccExpiration", "10/23");
        fillField("input#ccCVV", "123");
        browser.findElement(By.cssSelector("form#orderForm")).submit();
    }

    private void submitEmptyOrderForm() {
        assertThat(browser.getCurrentUrl()).isEqualTo(currentOrderDetailsPageUrl());
        fillField("input#deliveryName", "");
        fillField("input#deliveryStreet", "");
        fillField("input#deliveryCity", "");
        fillField("input#deliverySubject", "");
        browser.findElement(By.cssSelector("form#orderForm")).submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors.size()).isEqualTo(7);
        assertThat(validationErrors).containsExactlyInAnyOrder(
                "Delivery name is required",
                "Street is required",
                "City is required",
                "Russian Federation subject is required",
                "Not a valid credit card number",
                "Must be formatted MM/YY",
                "Invalid CVV"
        );
    }

    private List<String> getValidationErrorTexts() {
        List<WebElement> validationErrorElements = browser.findElements(By.className("validationError"));
        List<String> validationErrors = validationErrorElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        return validationErrors;
    }

    private void submitInvalidOrderForm() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        fillField("input#deliveryName", "I");
        fillField("input#deliveryStreet", "1");
        fillField("input#deliveryCity", "F");
        fillField("input#deliverySubject", "C");
        fillField("input#ccNumber", "1234432112344322");
        fillField("input#ccExpiration", "14/91");
        fillField("input#ccCVV", "1234");
        browser.findElement(By.cssSelector("form#orderForm")).submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors.size()).isEqualTo(3);
        assertThat(validationErrors).containsExactlyInAnyOrder(
                "Not a valid credit card number",
                "Must be formatted MM/YY",
                "Invalid CVV"
        );
    }

    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElement(By.cssSelector(fieldName));
        field.clear();
        field.sendKeys(value);
    }

    private void assertIngredient(WebElement ingredientGroup,
                                  int ingredientIdx, String id, String name) {
        List<WebElement> proteins = ingredientGroup.findElements(By.tagName("div"));
        WebElement ingredient = proteins.get(ingredientIdx);

        assertThat(
                ingredient.findElement(By.tagName("input")).getAttribute("value")
        ).isEqualTo(id);

        assertThat(
                ingredient.findElement(By.tagName("span")).getText()
        ).isEqualTo(name);
    }

    private void clickDesignASandwich() {
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        browser.findElement(By.cssSelector("a[id='design']")).click();
    }

    private void clickBuildAnotherSandwich() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        browser.findElement(By.cssSelector("a[id='another']")).click();
    }


    //
    // URL helper methods
    //
    private String loginPageUrl() {
        return homePageUrl() + "login";
    }

    private String registrationPageUrl() {
        return homePageUrl() + "register";
    }
    private String designPageUrl() {
        return homePageUrl() + "design";
    }

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private String currentOrderDetailsPageUrl() {
        return homePageUrl() + "orders/current";
    }

}
