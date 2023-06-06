package br.com.smashcode.api.agroconnect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
    private AuthorizationFilter authFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChainHandler(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authConfig -> {
                authConfig.requestMatchers(HttpMethod.POST,"/usuario/signup").permitAll();
                authConfig.requestMatchers(HttpMethod.POST,"/login").permitAll();
                authConfig.requestMatchers(HttpMethod.GET,"/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll();
                authConfig.requestMatchers("/h2-console").permitAll();
                authConfig.anyRequest().authenticated();
            })
            .csrf(c -> c.disable())
            .formLogin(l -> l.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(h -> h.frameOptions(f -> f.sameOrigin()))
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .cors(Customizer.withDefaults())
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoderHandler() {
        return new BCryptPasswordEncoder();
    }
}
