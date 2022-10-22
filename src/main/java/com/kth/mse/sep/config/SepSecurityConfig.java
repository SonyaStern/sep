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
        UserDetails am = User.withUsername("am")
            .password(passwordEncoder().encode("amPassword"))
            .roles("AM")
            .build();
        UserDetails fm = User.withUsername("fm")
            .password(passwordEncoder().encode("fmPassword"))
            .roles("FM")
            .build();
        UserDetails pm = User.withUsername("pm")
            .password(passwordEncoder().encode("pmPassword"))
            .roles("PM")
            .build();
        UserDetails sm = User.withUsername("sm")
            .password(passwordEncoder().encode("smPassword"))
            .roles("SM")
            .build();
        UserDetails csm = User.withUsername("csm")
            .password(passwordEncoder().encode("csmPassword"))
            .roles("CSM")
            .build();
        UserDetails scsm = User.withUsername("scsm")
            .password(passwordEncoder().encode("scsmPassword"))
            .roles("SCSM")
            .build();
        UserDetails staff = User.withUsername("staff")
            .password(passwordEncoder().encode("staffPassword"))
            .roles("STAFF")
            .build();
        return new InMemoryUserDetailsManager(am, fm, pm, sm, csm, scsm, staff);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
