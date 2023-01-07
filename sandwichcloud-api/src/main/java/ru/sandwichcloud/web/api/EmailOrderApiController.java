package ru.sandwichcloud.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="email-api/orders/from-email", produces="application/json")
@CrossOrigin(origins="http://sandwichcloud:8080")
public class EmailOrderApiController {

    private String lastEmailOrder = null;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveEmailOrder(@RequestBody String emailOrder) {
        lastEmailOrder = emailOrder;
        return lastEmailOrder;
    }

    @GetMapping
    public String getLastEmailOrder() {
        return lastEmailOrder == null
                ? "There are not any email orders yet"
                : "Last email order:\n" + lastEmailOrder;
    }
}
