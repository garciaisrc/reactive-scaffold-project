package co.com.autenthication.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long jwtExpiration = 1000 * 60 * 60; // 1 hora

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }


    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && extractExpiration(token).after(new Date());
    }
    public Authentication getAuthentication(String token) {
        String username = extractUsername(token);
        String role = extractRole(token);

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        return new UsernamePasswordAuthenticationToken(username, null, List.of(authority));
    }
}
