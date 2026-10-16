package se.glamourrental.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.glamourrental.dto.ItemRequest;
import se.glamourrental.dto.ItemResponse;
import se.glamourrental.service.ItemService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Klänningar", description = "Festklänningar och brudklänningar som kan hyras")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    @Operation(summary = "Visa alla klänningar")
    public List<ItemResponse> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Visa en klänning")
    public ItemResponse getById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @PostMapping
    @Operation(summary = "Lägg till en klänning")
    public ResponseEntity<ItemResponse> create(@Valid @RequestBody ItemRequest request) {
        ItemResponse created = itemService.create(request);
        return ResponseEntity.created(URI.create("/api/items/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Ändra en klänning")
    public ItemResponse update(@PathVariable Long id, @Valid @RequestBody ItemRequest request) {
        return itemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Ta bort en klänning")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
