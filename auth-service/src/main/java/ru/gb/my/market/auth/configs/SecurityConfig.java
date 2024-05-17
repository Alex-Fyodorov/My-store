package ru.gb.my.market.auth.configs;

import ru.gb.my.market.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@Slf4j
@RequiredArgsConstructor
@PropertySource("secrets.properties")
public class SecurityConfig {
    private final UserService userService;
    //private final AuthenticationManager authenticationManager;
    //private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("===============================================================");
        log.info("Dao Authentication Provider");
        http
                //.csrf(csrf -> csrf.disable()) // Отключаем csrf-токен,
                // так как если фронт не генерится на беке, он бесполезен.
                .csrf(AbstractHttpConfigurer::disable) // То же самое, но по-другому записано.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Для безопасности отключаются сессии. Теперь у контекста привязки к сессии нет.
                .authorizeHttpRequests(authorise -> authorise
//                        .requestMatchers(HttpMethod.POST, "/api/v1/products").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/products").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PATCH, "/api/v1/products").hasRole("ADMIN")
//                        .requestMatchers("api/v1/orders/**").authenticated()
                        .requestMatchers("/auth").permitAll()
                        .anyRequest().permitAll())
                .exceptionHandling(eh -> eh.authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))); // Обработка ошибок.
                // В случае попытки несанкционированного запроса возвращает ошибку 401.
        //http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/home1/**",
//                "/unsecured/**",
//                "/auth"
//                //"/api/v1/products/**",
//                //"/api/v1/carts/**"
//        );
//    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserService userDetailsService,
            BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }
}
