package Farmacia_Pucon.demo.authentication.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET = "UnaClaveMuyLargaYSecretaQueCambiaLuego_1234567890";
    private final long EXPIRATION = 86400000; // 1 día

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // EXTRAER USERNAME DESDE TOKEN
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // EXTRAER EXPIRACIÓN
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // EXTRAER TODOS LOS CLAIMS
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // EXTRAER CUALQUIER CLAIM
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // VALIDAR EXPIRACIÓN
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // VALIDAR TOKEN COMPLETO
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 🔥 GENERAR TOKEN CON ROLES
    public String generateToken(UserDetails userDetails) {

        // Convertir authorities a set de Strings
        Set<String> roles = new HashSet<>();
        userDetails.getAuthorities().forEach(a -> roles.add(a.getAuthority()));

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles); // Agregar roles al token

        return createToken(claims, userDetails.getUsername());
    }

    // CREAR TOKEN CON CLAIMS Y USUARIO
    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // EXTRAER ROLES DEL TOKEN
    public Set<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);

        Object rolesObject = claims.get("roles");

        if (rolesObject instanceof List<?>) {
            List<?> list = (List<?>) rolesObject;
            Set<String> roles = new HashSet<>();
            list.forEach(item -> roles.add(item.toString()));
            return roles;
        }

        return Collections.emptySet();
    }
}
