package Farmacia_Pucon.demo.authentication.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    private final String SECRET = "UnaClaveMuyLargaYSecretaQueCambiaLuego_1234567890";
    private final long EXPIRATION = 86400000; // 1 día

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ===================== GENERAR TOKEN =====================
    public String generateToken(String username, Set<String> roles) {

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)   // roles dentro del token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ===================== OBTENER USERNAME =====================
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ===================== OBTENER ROLES =====================
    public Set<String> extractRoles(String token) {
        return (Set<String>) extractAllClaims(token).get("roles");
    }

    // ===================== VALIDAR TOKEN =====================
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);  // Si no lanza excepción → válido
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===================== EXTRAER CLAIMS =====================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
