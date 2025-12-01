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

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con username: " + username)
                );

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Roles en mayúsculas
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getNombre().toUpperCase()))
        );

// Permisos en mayúsculas
// user.getRoles().forEach(role ->
//        role.getPermissions().forEach(permission ->
//                authorities.add(new SimpleGrantedAuthority(permission.getCodigo().toUpperCase()))
//        )
// );

        // Crear UserDetails asegurando account activo
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.getActivo())  // activo=false => disabled=true
                .build();
    }
}