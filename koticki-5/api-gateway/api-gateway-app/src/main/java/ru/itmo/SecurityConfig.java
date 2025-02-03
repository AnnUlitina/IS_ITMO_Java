package ru.itmo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.itmo.service.SecurityUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@ComponentScan
public class SecurityConfig {
    private final SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    public SecurityConfig(SecurityUserDetailsService userDetailsService) {
        this.securityUserDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                        authorization -> {
                            authorization.requestMatchers("/user/**").hasAnyAuthority("ADMIN");
                            authorization.requestMatchers(HttpMethod.GET, "/owners/**").hasAnyAuthority("USER", "ADMIN");
                            authorization.requestMatchers(HttpMethod.GET, "/cats/**").hasAnyAuthority("USER", "ADMIN");
                            authorization.requestMatchers(HttpMethod.DELETE, "/cats/**").hasAuthority("ADMIN");
                            authorization.requestMatchers(HttpMethod.POST, "/cats/**").hasAuthority("ADMIN");
                            authorization.requestMatchers(HttpMethod.DELETE, "/owners/**").hasAuthority("ADMIN");
                            authorization.requestMatchers(HttpMethod.POST, "/owners/**").hasAuthority("ADMIN");
                            authorization.anyRequest().authenticated();
                        })
                .httpBasic(Customizer.withDefaults()).userDetailsService(securityUserDetailsService)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityUserDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
