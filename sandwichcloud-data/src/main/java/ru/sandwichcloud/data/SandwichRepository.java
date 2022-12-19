package ru.sandwichcloud.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.sandwichcloud.Sandwich;

public interface SandwichRepository extends CrudRepository<Sandwich, Long>, PagingAndSortingRepository<Sandwich, Long> {
}