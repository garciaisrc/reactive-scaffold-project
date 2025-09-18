package co.com.autenthication.api;

import co.com.autenthication.api.user.dto.ExistsUserResponse;
import co.com.autenthication.api.user.dto.UserRequestDTO;
import co.com.autenthication.api.user.mapper.UserMapper;
import co.com.autenthication.api.exceptions.ErrorResponse;
import co.com.autenthication.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.transaction.reactive.TransactionalOperator;


import static org.springframework.web.reactive.function.BodyInserters.fromValue;
@Slf4j
@Component
@RequiredArgsConstructor
@Configuration
public class Handler {
    private final UserUseCase userUseCase;

    private final TransactionalOperator transacionalOperator;


    public Mono<ServerResponse> registerUser(ServerRequest request) {
        log.info("Starting user registration process");
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
        log.info("Fetching user by ID: {}", id);
        return userUseCase.getUserById(id)
                .map(UserMapper::toResponse)
                .flatMap(response ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(response))
                )
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                        .bodyValue(ErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(e.getMessage())
                                .build())
                );
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        log.info("Deleting user with ID: {}", id);
        return userUseCase.deleteUserById(id)
                .then(ServerResponse.noContent().build());
    }
    public Mono<ServerResponse> listenGETUseCase(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue("GET use case demo")
         .as(transacionalOperator::transactional);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        log.info("Processing GET demo request");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUseCase.getAllUsers().map(UserMapper::toResponse), UserRequestDTO.class);
    }

    public Mono<ServerResponse> getUserByDocument(ServerRequest request) {
        String document = request.pathVariable("numDocumentUser");
        log.info("Searching user by document: {}", document);
        return userUseCase.getUserByDocument(document)
                .flatMap(user -> ServerResponse.ok().bodyValue(ExistsUserResponse.builder()
                        .exists(true)
                        .numDocumentUser(user.getNumDocument())
                        .emailApp(user.getEmail())
                        .firstName(user.getFirstName())
                        .baseSalary(user.getBaseSalary())
                        .build()))
                .switchIfEmpty(ServerResponse.ok().bodyValue(ExistsUserResponse.builder()
                        .exists(false)
                        .build()));
    }
}
