package com.fiftyfive.springboot.security;

import com.fiftyfive.springboot.security.opa.OPAAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    private OPAAuthorizationManager opaAuthorizationManager;


    @Bean
    public UserDetailsService userDetailsService() {

        var userDetailsService =
                new InMemoryUserDetailsManager();

        var adminUser = User.withUsername("Raman")
                .password(getPasswordEncoder().encode("password"))
                .authorities("all")
                .build();

        var readUser = User.withUsername("Aman")
                .password(getPasswordEncoder().encode("password"))
                .authorities("read", "write")
                .build();

        var writeUser = User.withUsername("Nial")
                .password(getPasswordEncoder().encode("password"))
                .authorities("write")
                .build();


        userDetailsService.createUser(adminUser);
        userDetailsService.createUser(readUser);
        userDetailsService.createUser(writeUser);


        return userDetailsService;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("In security filter chain ------------------------>");
        http.csrf().disable()
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/v1/users")
                                .access(opaAuthorizationManager)
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic()
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
