package se.glamourrental.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ItemRequest(
        @NotBlank(message = "Namn får inte vara tomt")
        @Size(max = 100, message = "Namn får vara högst 100 tecken")
        String name,

        @NotBlank(message = "Kategori får inte vara tom")
        @Size(max = 60, message = "Kategori får vara högst 60 tecken")
        String category,

        @NotBlank(message = "Storlek får inte vara tom")
        @Size(max = 20, message = "Storlek får vara högst 20 tecken")
        String size,

        @NotNull(message = "Pris måste anges")
        @DecimalMin(value = "0.01", message = "Pris måste vara större än 0")
        @Digits(integer = 8, fraction = 2, message = "Ange ett pris med högst två decimaler")
        BigDecimal pricePerDay
) {
}
