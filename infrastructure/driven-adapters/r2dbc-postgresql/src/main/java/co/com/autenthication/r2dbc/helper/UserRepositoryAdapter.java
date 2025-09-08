package co.com.autenthication.r2dbc.helper;

import co.com.autenthication.model.user.User;
import co.com.autenthication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserReactiveRepository repository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public Mono<User> save(User user) {
        log.debug("Intentando guardar usuario: {}", user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(UserMapper.toEntity(user))
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<User> findById(String id) {
        log.debug("Buscando usuario por id={}", id);
        return repository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public Flux<User>findAll() {
        log.debug("Consultando todos los usuarios");
        return repository.findAll()
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<Void>deleteById(String id) {
        log.debug("Eliminando usuario con id={}", id);
        return repository.deleteById(id);
    }
    @Override
    public Mono<User>findByDocument(String numDocument) {
        log.debug("Buscando usuario por documento={}", numDocument);
        return repository.findByNumDocument(numDocument)
                .map(UserMapper::toDomain);
    }
    @Override
    public Mono<User>findByEmail(String email) {
        log.debug("Buscando usuario por email={}", email);
        return repository.findByEmail(email)
                .map(UserMapper::toDomain);
    }
}
