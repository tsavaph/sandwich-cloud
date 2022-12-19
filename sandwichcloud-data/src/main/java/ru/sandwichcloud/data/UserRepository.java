package ru.sandwichcloud.data;

import org.springframework.data.repository.CrudRepository;
import ru.sandwichcloud.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
