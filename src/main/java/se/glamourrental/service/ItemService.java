package se.glamourrental.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.glamourrental.dto.ItemRequest;
import se.glamourrental.dto.ItemResponse;
import se.glamourrental.exception.ConflictException;
import se.glamourrental.exception.NotFoundException;
import se.glamourrental.model.Item;
import se.glamourrental.model.Person;
import se.glamourrental.repository.ItemRepository;
import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getAll() {
        return itemRepository.findAllByOrderByIdAsc().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ItemResponse getById(Long id) {
        return toResponse(findItem(id));
    }

    public ItemResponse create(ItemRequest request) {
        Item item = new Item(request.name().trim(), request.category().trim(),
                request.size().trim(), request.pricePerDay());
        return toResponse(itemRepository.save(item));
    }

    public ItemResponse update(Long id, ItemRequest request) {
        Item item = findItem(id);
        item.setName(request.name().trim());
        item.setCategory(request.category().trim());
        item.setSize(request.size().trim());
        item.setPricePerDay(request.pricePerDay());
        return toResponse(item);
    }

    public void delete(Long id) {
        Item item = findItem(id);
        // Om klänningen har en koppling ska den inte raderas direkt.
        if (!item.getPersons().isEmpty()) {
            throw new ConflictException("Klänningen är kopplad till en person. Ta bort kopplingarna först.");
        }
        itemRepository.delete(item);
    }

    private Item findItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Klänning med id " + id + " finns inte."));
    }

    private ItemResponse toResponse(Item item) {
        List<Long> personIds = item.getPersons().stream()
                .map(Person::getId).sorted().toList();
        return new ItemResponse(item.getId(), item.getName(), item.getCategory(),
                item.getSize(), item.getPricePerDay(), personIds);
    }
}
