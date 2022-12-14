package ru.sandwichcloud;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class SandwichOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private User user;

    private Date placedAt;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @NotBlank(message="Street is required")
    private String deliveryStreet;

    @NotBlank(message="City is required")
    private String deliveryCity;

    @NotBlank(message="Russian Federation subject is required")
    private String deliverySubject;

    @CreditCardNumber(message="Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message="Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;

    @ManyToMany(targetEntity = Sandwich.class)
    private List<Sandwich> sandwiches = new ArrayList<>();

    public void addSandwich(Sandwich sandwich) {
        this.sandwiches.add(sandwich);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
