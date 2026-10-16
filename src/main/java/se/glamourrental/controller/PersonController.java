package se.glamourrental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.glamourrental.dto.PersonRequest;
import se.glamourrental.dto.PersonResponse;
import se.glamourrental.service.PersonService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/persons")
@Tag(name = "Personer", description = "Personer som hyr klänningar")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Operation(summary = "Visa alla personer")
    public List<PersonResponse> getAll() {
        return personService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Visa en person")
    public PersonResponse getById(@PathVariable Long id) {
        return personService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Lägg till en person")
    public ResponseEntity<PersonResponse> create(@Valid @RequestBody PersonRequest request) {
        // Här tar jag emot personens uppgifter som JSON och kontrollerar att de är giltiga.
        PersonResponse created = personService.create(request);
        return ResponseEntity.created(URI.create("/api/persons/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ändra en person")
    public PersonResponse update(@PathVariable Long id, @Valid @RequestBody PersonRequest request) {
        return personService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ta bort en person")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }

    @PostMapping("/{personId}/items/{itemId}")
    @Operation(summary = "Koppla en klänning till en person")
    public PersonResponse addItem(@PathVariable Long personId, @PathVariable Long itemId) {
        return personService.addItem(personId, itemId);
    }

    @DeleteMapping("/{personId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ta bort kopplingen mellan person och klänning")
    public void removeItem(@PathVariable Long personId, @PathVariable Long itemId) {
        personService.removeItem(personId, itemId);
    }
}
