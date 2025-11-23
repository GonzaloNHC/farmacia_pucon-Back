package Farmacia_Pucon.demo.authentication.security;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
     * Carga el usuario desde la base de datos, y mapea:
     *  - Roles       -> Authorities
     *  - Permisos    -> Authorities
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con username: " + username)
                );

        // Autoridades finales: roles + permisos
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Agregar roles como authority
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getNombre()))
        );

        // Agregar permisos desde cada rol
        user.getRoles().forEach(role ->
                role.getPermissions().forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(permission.getCodigo()))
                )
        );

        // Crear UserDetails que Spring Security necesita
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
