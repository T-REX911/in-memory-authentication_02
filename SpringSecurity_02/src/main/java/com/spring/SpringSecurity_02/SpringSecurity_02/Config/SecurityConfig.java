package com.spring.SpringSecurity_02.SpringSecurity_02.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/product/create").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/product/update").hasAnyRole("ADMIN", "MANAGER", "CASHIER")
                        .requestMatchers("/product/delete").hasRole("ADMIN")
                        .requestMatchers("/product/read").hasAnyRole("ADMIN", "MANAGER", "CASHIER", "USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Use basic auth
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                //User.withUsername("admin").password("admin123").roles("ADMIN").build(), //Old method without encryption
                User.withUsername("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").build(),
                User.withUsername("manager").password(passwordEncoder().encode("manager123")).roles("MANAGER").build(),
                User.withUsername("cashier").password(passwordEncoder().encode("cashier123")).roles("CASHIER").build(),
                User.withUsername("user").password(passwordEncoder().encode("user123")).roles("USER").build()
        );
    }

    /*@SuppressWarnings("deprecation")
    @Bean
    public static org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        // No password encryption, for simplicity
        return NoOpPasswordEncoder.getInstance();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use a strong encoder like BCrypt
    }
}
