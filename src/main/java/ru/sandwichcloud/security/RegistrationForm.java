package ru.sandwichcloud.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sandwichcloud.User;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String subject;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {

        return new User(
                username, passwordEncoder.encode(password),
                fullname, street, city, subject, phone
        );
    }
}
