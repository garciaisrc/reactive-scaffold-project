package co.com.autenthication.api.user.mapper;
import co.com.autenthication.api.user.dto.UserRequestDTO;
import co.com.autenthication.api.user.dto.UserResponseDTO;
import co.com.autenthication.model.user.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .numDocument(dto.getNumDocument())
                .numPhone(dto.getNumPhone())
                .idRol(dto.getIdRol())
                .baseSalary(dto.getBaseSalary())
                .password(dto.getPassword())
                .build();
    }

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .numDocument(user.getNumDocument())
                .numPhone(user.getNumPhone())
                .idRol(user.getIdRol())
                .baseSalary(user.getBaseSalary())
                .build();
    }
}
