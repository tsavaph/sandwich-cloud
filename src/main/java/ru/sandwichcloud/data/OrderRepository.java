package ru.sandwichcloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.sandwichcloud.SandwichOrder;

public interface OrderRepository extends CrudRepository<SandwichOrder, Long> {
}
