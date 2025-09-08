package co.com.autenthication.jwt.handler;

import co.com.autenthication.jwt.dto.LoginRequest;
import co.com.autenthication.jwt.dto.TokenResponse;
import co.com.autenthication.jwt.util.JwtUtil;
import co.com.autenthication.model.user.User;
import co.com.autenthication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(loginRequest ->
                        userRepository.findByEmail(loginRequest.getEmail()) // usca por email
                                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                                .flatMap(user -> validateCredentials(user, loginRequest))
                )
                .onErrorResume(e -> {
                    log.error("Error en login: {}", e.getMessage());
                    return ServerResponse.badRequest().bodyValue("Credenciales inválidas");
                });
    }
    private Mono<ServerResponse> validateCredentials(User user, LoginRequest loginRequest) {
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.info("Login exitoso para usuario {}", user.getEmail());

            String role = mapRol(user.getIdRol());
            String token = jwtUtil.generateToken(user.getEmail(), role);

            return ServerResponse.ok().bodyValue(new TokenResponse(token));
        } else {
            log.warn("Password incorrecto para usuario {}", user.getEmail());
            return ServerResponse.badRequest().bodyValue("Credenciales inválidas");
        }
    }
    private String mapRol(Long idRol) {
        return switch (idRol.intValue()) {
            case 1 -> "ADMINISTRADOR";case 2 -> "ASESOR";case 3 -> "CLIENTE";default -> "INVITADO";
        };
    }
}
