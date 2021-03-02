package com.hvt.booking_lux.security;

import com.hvt.booking_lux.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public SecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/","/static/**").permitAll()
                .anyRequest().permitAll().and()
                .formLogin().loginPage("/user/login").defaultSuccessUrl("/",true)
                .and().logout().logoutUrl("/user/logout").logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID").invalidateHttpSession(true).clearAuthentication(true);
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("testAdmin").password(passwordEncoder.encode("testAdmin")).roles("ADMIN");
        auth.userDetailsService(userService);
    }
}

