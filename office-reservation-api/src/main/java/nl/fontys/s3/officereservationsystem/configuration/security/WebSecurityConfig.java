package nl.fontys.s3.officereservationsystem.configuration.security;

import nl.fontys.s3.officereservationsystem.configuration.security.auth.AuthenticationRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class WebSecurityConfig {
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    private static final String END_POINT_LEAVE = "/leave/**";
    private static final String END_POINT_RESERVATION = "/reservations/**";
    private static final String END_POINT_ROOM = "/rooms/**";
    private static final String END_POINT_TOKEN = "/tokens/**";
    private static final String END_POINT_TABLE = "/table/**";
    private static final String END_POINT_TEAM = "/teams/**";
    private static final String END_POINT_USER = "/users/**";

    private static final String[] SWAGGER_UI_RESOURCES = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           AuthenticationRequestFilter authenticationRequestFilter,
                                           AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer ->
                        configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, END_POINT_ROOM, END_POINT_TEAM, END_POINT_USER).hasRole(ROLE_ADMIN)
//                                .requestMatchers(HttpMethod.POST, END_POINT_LEAVE, END_POINT_RESERVATION).hasAnyRole(ROLE_ADMIN, ROLE_USER)
//                                .requestMatchers(HttpMethod.POST, END_POINT_TOKEN).permitAll()
//                                .requestMatchers(HttpMethod.GET, END_POINT_LEAVE, END_POINT_RESERVATION, END_POINT_ROOM, END_POINT_TOKEN, END_POINT_TABLE, END_POINT_TEAM, END_POINT_USER).hasAnyRole(ROLE_ADMIN, ROLE_USER)
//                                .requestMatchers(HttpMethod.PUT, END_POINT_ROOM, END_POINT_USER).hasRole(ROLE_ADMIN)
//                                .requestMatchers(HttpMethod.PUT, END_POINT_TEAM).hasAnyRole(ROLE_ADMIN, ROLE_USER)
//                                .requestMatchers(HttpMethod.DELETE, END_POINT_ROOM, END_POINT_TEAM, END_POINT_USER).hasRole(ROLE_ADMIN)
//                                .requestMatchers(HttpMethod.DELETE, END_POINT_LEAVE, END_POINT_RESERVATION).hasAnyRole(ROLE_ADMIN, ROLE_USER)
                                .requestMatchers(SWAGGER_UI_RESOURCES).permitAll()
                                .requestMatchers(HttpMethod.POST, "/tokens").permitAll()
                                .anyRequest().permitAll())
                .exceptionHandling(configure -> configure.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(authenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED); // Sends 401 Unauthorized status
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")// Remove trailing slash
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials if necessary

            }
        };
    }

}
