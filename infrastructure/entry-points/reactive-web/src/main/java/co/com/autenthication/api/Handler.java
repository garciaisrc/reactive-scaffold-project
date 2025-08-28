package co.com.autenthication.api;

import co.com.autenthication.api.user.dto.UserRequestDTO;
import co.com.autenthication.api.user.mapper.UserMapper;
import co.com.autenthication.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.transaction.reactive.TransactionalOperator;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
@Configuration
public class Handler {
    private final UserUseCase userUseCase;

    private final TransactionalOperator transacionalOperator;

    public Mono<ServerResponse> registerUser(ServerRequest request) {
        return request.bodyToMono(UserRequestDTO.class)
                .map(UserMapper::toEntity)
                .flatMap(userUseCase::registerUser)
                .map(UserMapper::toResponse)
                .flatMap(response ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(response))
                );
    }
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return userUseCase.getUserById(id)
                .map(UserMapper::toResponse)
                .flatMap(response ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(response))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return userUseCase.deleteUserById(id)
                .then(ServerResponse.noContent().build());
    }
    public Mono<ServerResponse> listenGETUseCase(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue("GET use case demo");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue("GET other use case demo");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest request) {
        return request.bodyToMono(String.class)
                .flatMap(body -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue("POST use case demo: " + body))
        .as(transacionalOperator::transactional);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUseCase.getAllUsers().map(UserMapper::toResponse), UserRequestDTO.class);
    }
}
