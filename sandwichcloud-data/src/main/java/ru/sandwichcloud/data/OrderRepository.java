package ru.sandwichcloud.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.sandwichcloud.SandwichOrder;
import ru.sandwichcloud.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<SandwichOrder, Long> {
    List<SandwichOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
