package se.glamourrental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Här kontrollerar jag att personens namn och e-post är giltiga.
public record PersonRequest(
        @NotBlank(message = "Namn får inte vara tomt")
        @Size(max = 100, message = "Namn får vara högst 100 tecken")
        String name,

        @NotBlank(message = "E-post får inte vara tom")
        @Email(message = "Ange en giltig e-postadress")
        @Size(max = 150, message = "E-post får vara högst 150 tecken")
        String email
) {
}
