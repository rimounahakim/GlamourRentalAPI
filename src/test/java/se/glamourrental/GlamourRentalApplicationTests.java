package se.glamourrental;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.glamourrental.dto.ItemRequest;
import se.glamourrental.dto.PersonRequest;
import se.glamourrental.exception.ConflictException;
import se.glamourrental.exception.NotFoundException;
import se.glamourrental.service.ItemService;
import se.glamourrental.service.PersonService;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GlamourRentalApplicationTests {

    @Autowired PersonService personService;
    @Autowired ItemService itemService;
    @Autowired Validator validator;

    @Test
    void personCanHaveSeveralItems() {
        var person = personService.create(new PersonRequest("Test Person", "test@example.com"));
        var item1 = itemService.create(new ItemRequest("Klänning A", "Fest", "S", new BigDecimal("200")));
        var item2 = itemService.create(new ItemRequest("Klänning B", "Bröllop", "M", new BigDecimal("300")));
        personService.addItem(person.id(), item1.id());
        personService.addItem(person.id(), item2.id());
        assertEquals(2, personService.getById(person.id()).itemIds().size());
        assertTrue(itemService.getById(item1.id()).personIds().contains(person.id()));
    }

    @Test
    void linkedPersonCannotBeDeleted() {
        var person = personService.create(new PersonRequest("Test Person", "test2@example.com"));
        var item = itemService.create(new ItemRequest("Klänning C", "Fest", "L", new BigDecimal("250")));
        personService.addItem(person.id(), item.id());
        assertThrows(ConflictException.class, () -> personService.delete(person.id()));
        assertThrows(ConflictException.class, () -> itemService.delete(item.id()));
        personService.removeItem(person.id(), item.id());
        assertDoesNotThrow(() -> itemService.delete(item.id()));
        assertDoesNotThrow(() -> personService.delete(person.id()));
    }

    @Test
    void missingItemGivesNotFound() {
        assertThrows(NotFoundException.class, () -> itemService.getById(999999L));
    }

    @Test
    void invalidPriceAndEmailAreRejected() {
        assertFalse(validator.validate(new PersonRequest("", "inte-epost")).isEmpty());
        assertFalse(validator.validate(new ItemRequest("Test", "Fest", "S", new BigDecimal("-50"))).isEmpty());
    }
}
