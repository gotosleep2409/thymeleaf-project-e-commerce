package com.example.shop.config;

import com.example.shop.serviceImpl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    /*@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
*/
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers("/*", "/home","/product/**","/categories/**","/shopping-cart/**"/*,"/admin/**"*/).permitAll()
                                /*.requestMatchers("/admin/**").hasAnyRole("ADMIN")*/
                                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").loginProcessingUrl("/login")
                        .usernameParameter("username").passwordParameter("password")
                        .defaultSuccessUrl("/admin/",true))
                .logout(logout ->logout.logoutUrl("/admin-logout").logoutSuccessUrl("/login"));

        return http.build();
    }
    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("resources/static/**","assets/**","uploads/**");
    }


}
