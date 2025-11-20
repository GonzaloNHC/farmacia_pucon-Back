package Farmacia_Pucon.demo.authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /** BCrypt para contraseñas */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** AuthenticationManager para el login */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /** Configuración real de seguridad */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(cs -> cs.disable())
                .cors(cors -> cors.disable())

                /** 🔥 Rutas PUBLICAS (sin login) */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/login",         // login
                                "/v3/api-docs/**",         // swagger
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        /** 🔥 Rutas protegidas por rol */
                        .requestMatchers("/api/users/**").hasAuthority("ADMINISTRADOR")
                        .requestMatchers("/api/roles/**").hasAuthority("ADMINISTRADOR")

                        /** 🔥 Rutas que solo necesitan estar autenticadas */
                        .anyRequest().authenticated()
                )

                /** 🔥 Registrar el filtro JWT ANTES de UsernamePasswordAuthenticationFilter */
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
