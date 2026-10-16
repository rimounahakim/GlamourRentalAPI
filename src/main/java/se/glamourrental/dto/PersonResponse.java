package se.glamourrental.dto;

import java.util.List;

public record PersonResponse(Long id, String name, String email, List<Long> itemIds) {
}
