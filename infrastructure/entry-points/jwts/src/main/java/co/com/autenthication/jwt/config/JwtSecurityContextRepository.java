package co.com.autenthication.jwt.config;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

    private final ServerAuthenticationConverter authenticationConverter;
    private final ReactiveAuthenticationManager authenticationManager;

    public JwtSecurityContextRepository(ServerAuthenticationConverter authenticationConverter,
                                        ReactiveAuthenticationManager authenticationManager) {
        this.authenticationConverter = authenticationConverter;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        // Stateless JWT → no se guarda nada en sesión
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return authenticationConverter.convert(exchange)
                .flatMap(authenticationManager::authenticate)
                .map(SecurityContextImpl::new);
    }
}

