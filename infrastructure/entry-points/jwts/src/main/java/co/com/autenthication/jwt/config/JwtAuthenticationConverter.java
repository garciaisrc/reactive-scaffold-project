package co.com.autenthication.jwt.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return Mono.just(new JwtPreAuthenticationToken(token));
        }
        return Mono.empty();
    }

    private static class JwtPreAuthenticationToken extends AbstractAuthenticationToken {
        private final String token;

        JwtPreAuthenticationToken(String token) {
            super(null);
            this.token = token;
            setAuthenticated(false);
        }

        @Override public Object getCredentials() { return token; }
        @Override public Object getPrincipal() { return null; }
    }
}
