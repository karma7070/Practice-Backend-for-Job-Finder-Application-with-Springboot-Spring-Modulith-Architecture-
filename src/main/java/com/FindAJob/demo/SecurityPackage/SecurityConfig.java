package com.FindAJob.demo.SecurityPackage;

import com.FindAJob.demo.reg_users.internal.Reg_UsersRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

//password hasher
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

//initialization of filter with jwt service and userRepo
    @Bean
    public AuthFilter authFilter(JWTService jwtSvc, Reg_UsersRepository userRepo) {
        return new AuthFilter(jwtSvc, userRepo);
    }

//
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration(AuthFilter filter) {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }


//Provider has no access to database, instead it uses references to
// direct requests to other functions that can communicate with the DB

    @Bean
    public AuthenticationProvider authProvider(CustomerUserDetailsService userDetServ,
                                               PasswordEncoder encoder){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetServ);
        provider.setPasswordEncoder(encoder);

        return provider;
    }
//manages the different filters if we have more than one
    @Bean
    public AuthenticationManager authManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthFilter authFilter
                                                   ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/app/users/create", "/app/users/logIn").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        authFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
