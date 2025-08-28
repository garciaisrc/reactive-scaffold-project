package co.com.autenthication.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String email;

    @Column("num_document")
    private String numDocument;

    @Column("num_phone")
    private String numPhone;

    @Column("id_rol")
    private Double idRol;

    @Column("base_salary")
    private BigDecimal baseSalary;
}
