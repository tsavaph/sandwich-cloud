package ru.sandwichcloud.data;

import org.springframework.data.repository.CrudRepository;

import ru.sandwichcloud.Sandwich;

public interface SandwichRepository extends CrudRepository<Sandwich, Long> {

}
