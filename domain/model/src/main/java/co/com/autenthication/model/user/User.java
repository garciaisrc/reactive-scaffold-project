package co.com.autenthication.model.user;
import lombok.*;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String numDocument;
    private String numPhone;
    private Double idRol;
    private BigDecimal baseSalary;
}
