
package se.glamourrental.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.glamourrental.dto.PersonRequest;
import se.glamourrental.dto.PersonResponse;
import se.glamourrental.exception.ConflictException;
import se.glamourrental.exception.NotFoundException;
import se.glamourrental.model.Item;
import se.glamourrental.model.Person;
import se.glamourrental.repository.ItemRepository;
import se.glamourrental.repository.PersonRepository;
import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final ItemRepository itemRepository;

    public PersonService(PersonRepository personRepository, ItemRepository itemRepository) {
        this.personRepository = personRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public List<PersonResponse> getAll() {
        return personRepository.findAllByOrderByIdAsc().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PersonResponse getById(Long id) {
        return toResponse(findPerson(id));
    }

    public PersonResponse create(PersonRequest request) {
        Person person = new Person(request.name().trim(), request.email().trim());
        return toResponse(personRepository.save(person));
    }

    public PersonResponse update(Long id, PersonRequest request) {
        Person person = findPerson(id);
        person.setName(request.name().trim());
        person.setEmail(request.email().trim());
        return toResponse(person);
    }

    public void delete(Long id) {
        Person person = findPerson(id);

        // Jag nekar radering om personen har lånade klänningar.
        if (!person.getItems().isEmpty()) {
            throw new ConflictException("Personen har kopplade klänningar. Ta bort kopplingarna först.");
        }

        personRepository.delete(person);
    }

    public PersonResponse addItem(Long personId, Long itemId) {
        Person person = findPerson(personId);
        Item item = findItem(itemId);

        boolean alreadyAdded = person.getItems().stream()
                .anyMatch(i -> i.getId().equals(itemId));

        if (alreadyAdded) {
            throw new ConflictException("Personen har redan den här klänningen kopplad.");
        }

        // Jag kopplar klänningen till personen åt båda hållen.
        person.getItems().add(item);
        item.getPersons().add(person);

        return toResponse(person);
    }

    public void removeItem(Long personId, Long itemId) {
        Person person = findPerson(personId);
        Item item = findItem(itemId);

        boolean removed = person.getItems().removeIf(
                i -> i.getId().equals(itemId)
        );

        if (!removed) {
            throw new NotFoundException("Personen har ingen koppling till den klänningen.");
        }

        // Jag tar bort kopplingen från båda sidorna.
        item.getPersons().removeIf(p -> p.getId().equals(personId));
    }

    private Person findPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person med id " + id + " finns inte."));
    }

    private Item findItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Klänning med id " + id + " finns inte."));
    }

    private PersonResponse toResponse(Person person) {
        List<Long> itemIds = person.getItems().stream()
                .map(Item::getId).sorted().toList();

        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getEmail(),
                itemIds
        );
    }
}
