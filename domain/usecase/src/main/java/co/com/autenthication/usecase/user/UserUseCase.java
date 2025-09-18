package co.com.autenthication.usecase.user;

import co.com.autenthication.model.user.User;
import co.com.autenthication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;



@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    /**
     * Registrar un nuevo usuario
     */
    public Mono<User> registerUser(User user) {
        return validateUser(user)
                .then(validateEmailNotExists(user.getEmail()))
                .then(userRepository.save(user));
    }

    /**
     * Obtener todos los usuarios
     */
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Buscar usuario por id
     */
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Eliminar usuario por id
     */
    public Mono<Void> deleteUserById(String id) {
        return userRepository.deleteById(id);
    }

    private Mono<Void> validateUser(User user) {
        return validateNotBlank(user.getFirstName(), "El nombre no puede estar vacio")
                .then(validateNotBlank(user.getLastName(), "El apellido no puede estar vacio"))
                .then(validateNotBlank(user.getEmail(), "El correo electronico no puede estar vacio"))
                .then(validateSalary(user.getBaseSalary()));
    }

    private Mono<Void> validateNotBlank(String value, String message) {
        return (value == null || value.isBlank()) ? Mono.error(new IllegalArgumentException(message)) : Mono.empty();
    }

    private Mono<Void> validateSalary(BigDecimal salary) {
        if (salary == null || salary.compareTo(BigDecimal.ZERO) < 0 || salary.compareTo(new BigDecimal("15000000")) > 0) {
            return Mono.error(new IllegalArgumentException("El salario base debe estar entre 0 y 15000000"));
        }
        return Mono.empty();
    }

    private Mono<Void> validateEmailNotExists(String email) {
        return userRepository.findAll()
                .filter(existing -> existing.getEmail().equalsIgnoreCase(email))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalArgumentException("El correo electrónico ya está registrado"));
                    }
                    return Mono.empty();
                });
    }

    public Mono<User> getUserByDocument(String numDocument) {
        return userRepository.findByDocument(numDocument);
    }
}
