package se.glamourrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.glamourrental.model.Person;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByOrderByIdAsc();
}
