package kg.neo.mobimarket_2.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/user/**")
                                .hasAuthority("USER")
                                .antMatchers("/product/**")
                                .hasAuthority("USER")
                                .antMatchers("/registration/**",

                                        "/swagger-ui/",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources",
                                        "/swagger-resources/*",
                                        "/swagger-resources/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/webjars/**").permitAll()
//                                        "/swagger-ui/**",
//                                        "/swagger-ui.html",
//                                        "/swagger-ui/",
//                                        "/v3/api-docs",
//                                        "/v3/api-docs/swagger-config").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
