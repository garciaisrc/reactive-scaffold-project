package co.com.autenthication.jwt.config;

import co.com.autenthication.jwt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationConverter authenticationConverter;
    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtAuthenticationConverter authenticationConverter,
                          JwtUtil jwtUtil) {
        this.authenticationConverter = authenticationConverter;
        this.jwtUtil = jwtUtil;
    }

    // Registramos JwtAuthenticationManager como bean
    @Bean
    public JwtAuthenticationManager jwtAuthenticationManager() {
        return new JwtAuthenticationManager(jwtUtil);
    }

    // Configuración principal de seguridad
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,JwtAuthenticationManager authenticationManager) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/login").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/users").permitAll()/*.hasAnyRole("ADMINISTRADOR", "ASESOR")*/
                        .pathMatchers(HttpMethod.GET, "/api/v1/users/document/**").permitAll()
                        .pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
                        .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager)
                .securityContextRepository(
                        new JwtSecurityContextRepository(authenticationConverter, authenticationManager)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((exchange, ex2) -> {
                            log.warn("Intento de acceso sin autenticacion. Path: {}",
                                    exchange.getRequest().getPath());
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            byte[] bytes = "{\"error\":\"No autorizado o token invalido\"}"
                                    .getBytes(StandardCharsets.UTF_8);
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                            return exchange.getResponse().writeWith(Mono.just(buffer));
                        })
                        .accessDeniedHandler((exchange, denied) -> {
                            log.error("Acceso denegado. Usuario autenticado pero sin permisos suficientes. Path: {}",
                                    exchange.getRequest().getPath());
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            byte[] bytes = "{\"error\":\"Acceso denegado\"}"
                                    .getBytes(StandardCharsets.UTF_8);
                            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                            return exchange.getResponse().writeWith(Mono.just(buffer));
                        })
                )
                .build();
    }
}
