package com.tdev.demo.webflux.config;

import com.tdev.demo.webflux.service.DevUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                    .pathMatchers(HttpMethod.POST, "/animes/**").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.GET, "/animes/**").hasRole("USER")
                .anyExchange().authenticated()
                .and()
                    .formLogin()
                .and()
                    .httpBasic()
                .and()
                    .build();
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager(DevUserDetailsService devUserDetailsService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(devUserDetailsService);
    }

    /*@Bean
    public MapReactiveUserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("dev"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("dev"))
                .roles("USER", "ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }*/

}
