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
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.internal.Logger;

import static org.hibernate.id.SequenceMismatchStrategy.LOG;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public static final String BEARER_AUTH = "bearerAuth";
    public static final String API_KEY = "Apikey";
    private static final String PERFIL_PRIVAT = "FUE_USUARI_OPFUE";

    @Autowired
    ResourceLoader resourceLoader;

    private static List<String> perfils;

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
                                .requestMatchers("/swagger-ui/**", "/api-doc/**", "/v3/**", "/swagger-ui.html", "/api/id/**").permitAll()
                                .requestMatchers("/js/**", "/css/**", "/img/**").permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/regla-tipo-prestacion/prestacions/**")
                                )
                                .permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/regla-tipo-prestacion/validation/**")
                                )
                                .permitAll()
                                .requestMatchers("/api/sql/**", "/cache/**").hasRole("API")
                                .requestMatchers("/regla-tipo-prestacion/list", "/sql/list").hasRole(PERFIL_PRIVAT)
                                .requestMatchers("/regla-tipo-prestacion/**", "/sql/**").hasRole(PERFIL_PRIVAT)
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

    @Bean
    public AuthenticationFailureHandler loginErrorHandler() {
        return new LoginErrorHandler();
    }

    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public AuthenticationProvider imiAuthenticationProvider() {
        return new SersocAuthenticationProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public Filter loginFilter(AuthenticationManager authManager){
        ExUsernamePasswordAuthenticationFilter result = new ExUsernamePasswordAuthenticationFilter();
        result.setAuthenticationManager(authManager);
        result.setAuthenticationFailureHandler(loginErrorHandler());
        result.setAuthenticationSuccessHandler(loginSuccessHandler());
        return result;
    }

    private String[] loadPerfils() {
        perfils = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:prfapl.txt");
        String line;
        try (
                InputStream in = resource.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in))
        ) {
            while ((line = br.readLine()) != null) { perfils.add(line); }
        } catch (IOException e) {
            LOG.error("OpfueFrontSecurityConfig.loadPerfils", e);
        }
        String[] ret = new String[perfils.size()];
        ret = perfils.toArray(ret);
        return ret;
    }
}

