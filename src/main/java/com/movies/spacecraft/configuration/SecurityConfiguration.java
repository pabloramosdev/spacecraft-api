package com.movies.spacecraft.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.GET,"/spacecrafts/**").hasAuthority("USER")
                               .requestMatchers(HttpMethod.POST,"/spacecrafts").hasAuthority("ADMIN")
                               .requestMatchers(HttpMethod.PUT,"/spacecrafts/**").hasAuthority("ADMIN")
                               .requestMatchers(HttpMethod.PATCH,"/spacecrafts/**").hasAuthority("ADMIN")
                               .requestMatchers(HttpMethod.DELETE,"/spacecrafts/**").hasAuthority("ADMIN")
                               .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}user").authorities("USER").build();
        UserDetails admin = User.withUsername("admin").password("{noop}admin").authorities("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
