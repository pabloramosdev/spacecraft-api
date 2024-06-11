package com.movies.spacecraft.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

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
                               .requestMatchers("/swagger-resources/*", "*.html", "/api/v1/swagger.json").hasAuthority("SWAGGER")
                               .requestMatchers("/h2-console/**").permitAll()
                               .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(conf -> conf.ignoringRequestMatchers(toH2Console()).disable())
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}user").authorities("USER").build();
        UserDetails swagger = User.withUsername("swagger").password("{noop}swagger").authorities("SWAGGER").build();
        UserDetails admin = User.withUsername("admin").password("{noop}admin").authorities("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, swagger, admin);
    }

}
