package Farmacia_Pucon.demo.authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Importaciones necesarias para la nueva sintaxis de Spring Boot 3.x
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;

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
                // Usando AbstractHttpConfigurer::disable para la nueva sintaxis (más limpio)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)

                // 🔥 CAMBIO CLAVE 1: Deshabilitar la protección X-Frame-Options para H2 Console
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))


                /** 🔥 Rutas PUBLICAS (sin login) */
                .authorizeHttpRequests(auth -> auth
                        // 🔥 CAMBIO CLAVE 2: Permitir acceso a la ruta de la consola H2
                        .requestMatchers("/h2/**").permitAll()

                        // Rutas públicas existentes
                        .requestMatchers(
                                "/api/auth/login",         // login
                                "/v3/api-docs/**",         // swagger
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/users/**",
                                "/api/roles/**"
                        ).permitAll()

                        /**  Rutas protegidas por rol */
                        //  .requestMatchers("/api/users/**").hasAuthority("ADMINISTRADOR")
                        // .requestMatchers("/api/roles/**").hasAuthority("ADMINISTRADOR")

                        /**  Rutas que solo necesitan estar autenticadas */
                        //.anyRequest().authenticated()
                )

                /** 🔥 Registrar el filtro JWT ANTES de UsernamePasswordAuthenticationFilter */
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}