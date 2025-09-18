package co.com.autenthication.r2dbc.helper;

import co.com.autenthication.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;
@DataR2dbcTest
@ActiveProfiles("test")
class UserReactiveRepositoryTest {
    @Autowired
    private UserReactiveRepository repository;

    @Test
    void shouldFindUsersAlreadyInserted() {
        Flux<UserEntity> users = repository.findAll();

        StepVerifier.create(users)
                .expectNextCount(5) // ya tienes 5 registros en la tabla
                .verifyComplete();
    }

}
