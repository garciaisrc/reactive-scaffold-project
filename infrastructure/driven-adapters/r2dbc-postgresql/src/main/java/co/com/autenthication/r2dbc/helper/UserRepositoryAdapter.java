package co.com.autenthication.r2dbc.helper;

import co.com.autenthication.model.user.User;
import co.com.autenthication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserReactiveRepository repository;

    @Override
    public Mono<User> save(User user) {
        return repository.save(UserMapper.toEntity(user))
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<User> findById(String id) {
        return repository.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public Flux<User> findAll() {
        return repository.findAll()
                .map(UserMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
