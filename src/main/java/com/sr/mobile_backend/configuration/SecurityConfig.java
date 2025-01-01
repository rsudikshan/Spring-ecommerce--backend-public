package com.sr.mobile_backend.configuration;

import com.sr.mobile_backend.filter.JwtFilter;
import com.sr.mobile_backend.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MyUserDetailsService service;

    @Autowired
    JwtFilter filter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        security.sessionManagement(customizer ->
                customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> customizer
                                .requestMatchers("/auth/**","/uploads/**","/products/**")
                                .permitAll()
                                .requestMatchers("/admin/**")
                                .hasRole("ADMIN")

                                .requestMatchers("/paymentManagement/**")
                                .hasRole("SUPER_ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

    @Bean
    public AuthenticationProvider provider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(service);
        return provider;

    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration)throws Exception{
         return configuration.getAuthenticationManager();
    }


}
//Config pass
//Auth pass
//Jwt Created
//Jwt Authentication complete
//config for file uploads pass
//admin pass
