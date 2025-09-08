package co.com.autenthication.r2dbc.helper;

import co.com.autenthication.model.user.User;
import co.com.autenthication.r2dbc.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .numDocument(entity.getNumDocument())
                .numPhone(entity.getNumPhone())
                .idRol(entity.getIdRol())
                .baseSalary(entity.getBaseSalary())
                .password(entity.getPassword())
                .build();
    }

    public static UserEntity toEntity(User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .email(domain.getEmail())
                .numDocument(domain.getNumDocument())
                .numPhone(domain.getNumPhone())
                .idRol(domain.getIdRol())
                .baseSalary(domain.getBaseSalary())
                .password(domain.getPassword())
                .build();
    }
}
