package org.arteco.sersoc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public static final String BEARER_AUTH = "bearerAuth";
    public static final String API_KEY = "Apikey";


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/swagger-ui/**", "/api-doc/**", "/v3/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/js/**", "/css/**", "/img/**").permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/regla-tipo-prestacion/prestacions/**")
                                )
                                .permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/regla-tipo-prestacion/validation/**")
                                )
                                .permitAll()
                                .requestMatchers("/api/sql/**", "/cache/**").hasRole("API")
                                .requestMatchers("/regla-tipo-prestacion/list", "/sql/list").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/regla-tipo-prestacion/**", "/sql/**").hasRole("ADMIN")
                                //.requestMatchers("/sql/**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginPage("/login").permitAll().successForwardUrl("/home"))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login"))
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/access-denied")
                        .defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(), request -> request.getRequestURI().startsWith("/api")))
                .addFilterBefore(apiKeyFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public Filter apiKeyFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(@NonNull HttpServletRequest request,
                                            @NonNull HttpServletResponse response,
                                            @NonNull FilterChain filterChain) throws ServletException, IOException {
                String apiKeyHeader = request.getHeader(API_KEY);
                if (StringUtils.hasText(apiKeyHeader) && apiKeyHeader.equals("abc123")) {
                    // Si el API Key coincide, establece la autenticación
                    Authentication auth = new AnonymousAuthenticationToken("API", "API", AuthorityUtils.createAuthorityList("ROLE_API"));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(API_KEY))
                )
                .info(new Info()
                        .title("Servicios Sociales API")
                        .version("1.0.0"));
    }
}

