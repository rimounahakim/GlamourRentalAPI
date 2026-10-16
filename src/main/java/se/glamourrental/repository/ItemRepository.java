package se.glamourrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.glamourrental.model.Item;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByIdAsc();
}
