package se.glamourrental;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "GlamourRentalAPI",
        version = "1.0",
        description = "REST API för att hyra festklänningar och brudklänningar."))
public class GlamourRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlamourRentalApplication.class, args);
    }
}
