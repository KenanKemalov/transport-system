package com.example.transportsystemj8.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //dali?
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/**", "/add/location", "/add/triptype", "/add/transporttype", "/register/**").hasAuthority("ADMIN")
                //.and().formLogin().loginPage("/login").defaultSuccessUrl("/admin/dashboard").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").
                .antMatchers("/company/**", "/create/trip", "/check/requests", "/check/sold/tickets").hasAuthority("COMPANY")
                .antMatchers("/request/tickets","/distributor/**").hasAuthority("DISTRIBUTOR")
                .antMatchers("/cashier/**, /sell/ticket").hasAuthority("CASHIER")
                .antMatchers("/create/cashier").hasAnyAuthority("ADMIN", "DISTRIBUTOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/default")
                .failureUrl("/login?error=true")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .invalidateHttpSession(true)
//                .logoutSuccessUrl("/login")
                //.logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
