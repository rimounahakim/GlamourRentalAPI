package se.glamourrental.dto;

import java.math.BigDecimal;
import java.util.List;

public record ItemResponse(Long id, String name, String category, String size,
                           BigDecimal pricePerDay, List<Long> personIds) {
}
