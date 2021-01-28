package com.musicapp.config;

import com.musicapp.security.filter.AuthenticationTokenFilter;
import com.musicapp.security.oauth2.Oauth2SuccessHandler;
import com.musicapp.security.oauth2.Oauth2UserServiceImpl;
import com.musicapp.security.oauth2.OidcUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * Конфигурация безопасности приложения.
 *
 * @author evgeniycheban
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_ENDPOINTS = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/auth/**",
            "/users/check-phone",
            "/users/check-email",
            "/users/create-by-phone",
            "/users/create-by-email",
            "/verification/email/**",
            "/verification/phone/check-phone-code",
            "/verification/phone/**/send-code",
            "/actuator/health",
            "/reset-password/**",
            "/user-subscription-email-code/**"
    };

    private final AuthenticationTokenFilter authenticationTokenFilter;
    private final UserDetailsService userDetailsService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final OidcUserServiceImpl oidcUserService;
    private final Oauth2UserServiceImpl oauth2UserService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/images/{fileName}").permitAll()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .and()
                .successHandler(oauth2SuccessHandler)
                .userInfoEndpoint()
                .userService(oauth2UserService)
                .oidcUserService(oidcUserService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
