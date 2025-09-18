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
@Builder(toBuilder = true)
public class ExistsUserResponse {
    @Schema(description = "Indica si el usuario existe", example = "true")
    private Boolean exists;
    @Schema(description = "Número de documento del usuario si existe", example = "123456789")
    private String numDocumentUser;
    @Schema(description = "Correo electrónico asociado", example = "daniel.gomez@example.com")
    private String emailApp;
    @Schema(description = "Primer nombre del usuario", example = "Daniel")
    private String firstName;

    @Schema(description = "Salario base del usuario", example = "2500000.00")
    private BigDecimal baseSalary;
}
