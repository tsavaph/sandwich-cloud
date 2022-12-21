package ru.sandwichcloud.web.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sandwichcloud.Sandwich;
import ru.sandwichcloud.data.SandwichRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="api/sandwiches", produces="application/json")
@CrossOrigin(origins="http://sandwichcloud:8080")
public class SandwichController {
    private SandwichRepository sandwichRepository;

    public SandwichController(SandwichRepository sandwichRepository) {
        this.sandwichRepository = sandwichRepository;
    }

    @GetMapping(params="recent")
    public Iterable<Sandwich> recentSandwiches() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        return sandwichRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public Optional<Sandwich> sandwichById(@PathVariable("id") Long id) {
        return sandwichRepository.findById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Sandwich postSandwich(@RequestBody Sandwich sandwich) {
        return sandwichRepository.save(sandwich);
    }

}
