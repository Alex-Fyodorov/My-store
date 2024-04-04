package gb.mystore.services;

import gb.mystore.entities.Role;
import gb.mystore.entities.User;
import gb.mystore.exceptions.ResourceNotFoundException;
import gb.mystore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User %s not found.", username)));
        return new org.springframework.security.core.userdetails.User(
                username, user.getPassword(), getAuthority(user.getRoles()));
    }

    public Collection<? extends GrantedAuthority> getAuthority(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public User saveNewUser(User user) {
        user.setId(null);
        Role role = roleService.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Role \"USER\" not found."));
        user.setRoles(List.of(role));
        user.setPassword(userServicePasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Bean
    public BCryptPasswordEncoder userServicePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
