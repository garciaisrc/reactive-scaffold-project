package co.com.autenthication.jwt.config;

import co.com.autenthication.jwt.util.JwtUtil;
import reactor.core.publisher.Mono;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        return Mono.justOrEmpty(jwtUtil.getAuthentication(token));
    }
}
