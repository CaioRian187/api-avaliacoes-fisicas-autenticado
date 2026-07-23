package com.CaioRian.AvaliacoesFisicas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(org.springframework.security.config.Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize


                    .requestMatchers("/",
                                     "/swagger-ui.html",
                                     "/swagger-ui/**",
                                     "/v3/api-docs",
                                     "/v3/api-docs/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/cadastro").permitAll()

                    .requestMatchers(HttpMethod.GET, "/api/user/listar-usuarios").hasAnyAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/user/nome").hasAnyAuthority("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/user").hasAnyAuthority("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/user").hasAnyAuthority("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/api/user/").hasAnyAuthority("ADMIN", "USER")

                    .requestMatchers(HttpMethod.GET, "/circunferencias/**").hasAnyAuthority("ADMIN", "USER")
                    .requestMatchers("/circunferencias/**").hasAuthority("ADMIN")

                    .requestMatchers(HttpMethod.GET, "/dobrasCutaneas/**").hasAnyAuthority("ADMIN", "USER")
                    .requestMatchers("/dobrasCutaneas/**").hasAuthority("ADMIN")

                    .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
