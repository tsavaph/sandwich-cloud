package ru.sandwichcloud.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sandwichcloud.data.UserRepository;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        log.info("LOG_LOG_LOG");
        log.info("username " + form.getUsername());
        log.info("password " + form.getPassword());
        log.info("fullname " + form.getFullname());
        log.info("street " + form.getStreet());
        log.info("city " + form.getCity());
        log.info("subject " + form.getSubject());
        log.info("phone " + form.getPhone());
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
