package com.godeltech.mastery.backend.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.godeltech.mastery.backend.security.AuthTokenFilter;
import com.godeltech.mastery.backend.security.JwtAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String API = "/api/v1";
  private static final String MANAGER_ADMIN_ROLES = "hasAnyRole('MANAGER','ADMIN')";
  private static final String ALL_ROLES = "hasAnyRole('USER','MANAGER','ADMIN')";

  private final PasswordEncoder bCryptPasswordEncoder;
  private final UserDetailsService clientDetailServiceImpl;
  private final JwtAuthEntryPoint jwtAuthEntryPoint;
  private final AuthTokenFilter authTokenFilter;

  @Value("${security.auth.whitelist}")
  private final String[] whitelist;

  @Override
  public void configure(final AuthenticationManagerBuilder authBuilder) throws Exception {
    authBuilder.userDetailsService(clientDetailServiceImpl).passwordEncoder(bCryptPasswordEncoder);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.cors(CorsConfigurer::disable)
        .csrf(CsrfConfigurer::disable)
        .authorizeRequests(
            auth ->
                auth.antMatchers(API + "/auth").permitAll()
                    .antMatchers(API + "/brands").access(ALL_ROLES)
                    .antMatchers(PATCH, API + "/cars/{\\d+}/photos").access(ALL_ROLES)
                    .antMatchers(API + "/cars/**").access(MANAGER_ADMIN_ROLES)
                    .antMatchers(API + "/clients/{\\d+}/cars").access(ALL_ROLES)
                    .antMatchers(API + "/clients").access(MANAGER_ADMIN_ROLES)
                    .antMatchers(API + "/clients/appointments").access(ALL_ROLES)
                    .antMatchers(API + "/equipments").access(ALL_ROLES)
                    .antMatchers(GET, API + "/guarantee/{\\d+}").access(ALL_ROLES)
                    .antMatchers(API + "/guarantee/**").access(MANAGER_ADMIN_ROLES)
                    .antMatchers(API + "/maintenances").access(ALL_ROLES)
                    .antMatchers(GET, API + "/operations/{\\d+}").access(ALL_ROLES)
                    .antMatchers(POST, API + "/operations/**").access(MANAGER_ADMIN_ROLES)
                    .antMatchers(GET, API + "/appointments").access(MANAGER_ADMIN_ROLES)
                    .antMatchers(POST, API + "/appointments").access(ALL_ROLES)
                    .antMatchers(PUT, API + "/appointments/{\\d+}").access(MANAGER_ADMIN_ROLES)
                    .anyRequest().permitAll())
        .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(final WebSecurity web) {
    web.ignoring().antMatchers(whitelist);
  }
}
