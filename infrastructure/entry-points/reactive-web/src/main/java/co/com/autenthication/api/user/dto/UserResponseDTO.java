package co.com.autenthication.api.user.dto;
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
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String numDocument;
    private String numPhone;
    private Double idRol;
    private BigDecimal baseSalary;
}
