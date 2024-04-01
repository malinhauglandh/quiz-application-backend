package org.ntnu.idi.idatt2105.project.config;

import org.ntnu.idi.idatt2105.project.security.JWTAuthorizationFilter;
import org.ntnu.idi.idatt2105.project.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Configuration class for security. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private TokenService tokenService;

    /**
     * Bean for security filter chain.
     *
     * @param http the http security.
     * @return the security filter chain.
     * @throws Exception if an error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .requestMatchers("/token")
                .permitAll()
                .requestMatchers("/login")
                .permitAll()
                .requestMatchers("/createUser")
                .permitAll()
                .requestMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(
                        new JWTAuthorizationFilter(tokenService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Bean for password encoder.
     *
     * @return a new BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter(TokenService tokenService) {
        return new JWTAuthorizationFilter(tokenService);
    }
}
