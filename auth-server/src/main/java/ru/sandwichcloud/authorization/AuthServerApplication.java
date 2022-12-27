package ru.sandwichcloud.authorization;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sandwichcloud.authorization.users.User;
import ru.sandwichcloud.authorization.users.UserRepository;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(
            UserRepository repo,
            PasswordEncoder encoder) {
        return args -> {
            repo.save(
                    new User("admin", encoder.encode("admin"), "ROLE_ADMIN"));
            repo.save(
                    new User("test", encoder.encode("test"), "ROLE_ADMIN"));
        };
    }
}
