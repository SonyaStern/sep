package com.kth.mse.sep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SepSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http builder configurations for authorize requests and form login (see below)
        http.csrf()
            .disable()
            .authorizeRequests()
            // use the antMatchers to control which roles have access to what
            // the roles are defined below with the user accounts
            .antMatchers("/administation/**")
            .hasRole("AM")
            .antMatchers("/production/**")
            .hasRole("PM")
            .antMatchers("/financial/**")
            .hasRole("FM")
            .antMatchers("/service/**")
            .hasRole("SM")
            .antMatchers("/customer_service/**")
            .hasRole("CSM")
            .antMatchers("/senior_customer_service/**")
            .hasRole("SCSM")
            .antMatchers("/staff/**")
            .hasRole("STAFF")
            .antMatchers("/anonymous*")
            .anonymous()
            .antMatchers("/login*")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginProcessingUrl("/perform_login")
            // .defaultSuccessUrl("/homepage.html", true)
            .and()
            .logout()
            .logoutUrl("/perform_logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/login.html");
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails u1 = User.withUsername("Mike")
            .password(passwordEncoder().encode("password"))
            .roles("SEE_EVENTS")
            .build();
        UserDetails u2 = User.withUsername("Janet")
                .password(passwordEncoder().encode("password"))
                .roles("SEE_EVENTS", "CREATE_EVENTS")
                .build();
        UserDetails u3 = User.withUsername("Jack")
                .password(passwordEncoder().encode("password"))
                .roles("SEE_EVENTS")
                .build();
        UserDetails u4 = User.withUsername("Natalie")
                .password(passwordEncoder().encode("password"))
                .roles("SEE_EVENTS")
                .build();
        UserDetails u5 = User.withUsername("Sarah")
                .password(passwordEncoder().encode("password"))
                .roles("SEE_EVENTS", "CREATE_EVENTS")
                .build();
        UserDetails u6 = User.withUsername("Judy")
                .password(passwordEncoder().encode("password"))
                .roles("SEE_EVENTS", "CREATE_EVENTS")
                .build();
        return new InMemoryUserDetailsManager(u1, u2, u3, u4, u5, u6);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
