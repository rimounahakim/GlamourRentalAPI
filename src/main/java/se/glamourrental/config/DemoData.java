package se.glamourrental.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.glamourrental.model.Item;
import se.glamourrental.model.Person;
import se.glamourrental.repository.ItemRepository;
import se.glamourrental.repository.PersonRepository;
import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DemoData {

    @Bean
    CommandLineRunner addDemoData(PersonRepository personRepository, ItemRepository itemRepository) {
        return args -> {
            // Här lägger jag in några klänningar och personer för att kunna testa mitt API.
            if (itemRepository.count() == 0) {
                itemRepository.saveAll(List.of(
                        new Item("Rosa sidenklänning", "Festklänning", "S", new BigDecimal("650.00")),
                        new Item("Vit brudklänning", "Brudklänning", "M", new BigDecimal("1500.00")),
                        new Item("Blå aftonklänning", "Festklänning", "L", new BigDecimal("750.00")),
                        new Item("Champagnefärgad balklänning", "Festklänning", "M", new BigDecimal("900.00"))
                ));
            }
            if (personRepository.count() == 0) {
                personRepository.saveAll(List.of(
                        new Person("Sara Andersson", "sara@example.com"),
                        new Person("Emma Nilsson", "emma@example.com")
                ));
            }
        };
    }
}
