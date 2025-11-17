package Farmacia_Pucon.demo.authentication.security;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Servicio que Spring Security utiliza para cargar los detalles del usuario
 * durante el login o la validación del JWT.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carga el usuario desde la base de datos y mapea sus roles a GrantedAuthority.
     * El nombre del rol se usa como la autoridad (ej: "ADMINISTRADOR").
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        // Mapea los roles (Role) a las Autoridades (GrantedAuthority)
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());

        // Retorna el objeto UserDetails que Spring Security necesita
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Contraseña hasheada
                authorities
        );
    }
}