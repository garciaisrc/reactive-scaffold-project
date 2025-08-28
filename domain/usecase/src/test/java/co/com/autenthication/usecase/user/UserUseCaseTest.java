package co.com.autenthication.usecase.user;

import co.com.autenthication.model.user.User;
import co.com.autenthication.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .firstName("Daniel")
                .lastName("Gomez")
                .email("daniel@example.com")
                .numDocument("123456789")
                .numPhone("3001234567")
                .idRol(1D)
                .baseSalary(BigDecimal.valueOf(2000000))
                .build();
    }

    @Test
    void shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Flux.just(user));

        StepVerifier.create(userUseCase.getAllUsers())
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById("1")).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.getUserById("1"))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldDeleteUserById() {
        when(userRepository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(userUseCase.deleteUserById("1"))
                .verifyComplete();

        verify(userRepository).deleteById("1");
    }
}
