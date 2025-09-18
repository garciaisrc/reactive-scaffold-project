package co.com.autenthication.api.user.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserResponseDTO {
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;
    @Schema(description = "Nombre del usuario", example = "Daniel")
    private String firstName;
    @Schema(description = "Apellido del usuario", example = "Gómez")
    private String lastName;
    @Schema(description = "Correo electrónico", example = "daniel.gomez@example.com")
    private String email;
    @Schema(description = "Número de documento", example = "123456789")
    private String numDocument;
    @Schema(description = "Número de teléfono", example = "3001234567")
    private String numPhone;
    @Schema(description = "Rol del usuario", example = "2")
    private Long idRol;
    @Schema(description = "Salario base del usuario", example = "2500000.00")
    private BigDecimal baseSalary;
}
