package co.com.autenthication.r2dbc.helper;

import co.com.autenthication.model.user.User;
import co.com.autenthication.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class UserRepositoryAdapterTest {

    @InjectMocks
    private UserRepositoryAdapter adapter;

    @Mock
    private UserReactiveRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteById_ShouldComplete() {
        when(repository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById("1"))
                .verifyComplete();
    }

}