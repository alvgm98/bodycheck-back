package bodycheck_back.bodycheck.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import bodycheck_back.bodycheck.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * Esta clase configura la SecurityFilterChain de la aplicación.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

   private final AuthenticationProvider authProvider;
   private final JwtAuthenticationFilter jwtAuthenticationFilter;

   @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http
            .csrf(csrf -> csrf.disable()) // Deshabilita la seguridad CSRF ya que la Autenticación se hará mediante JWT.
            .authorizeHttpRequests(authRequest -> authRequest
                  .requestMatchers("/auth/**").permitAll() // Permite acceso sin autenticacion al patrón de rutas.
                  .anyRequest().authenticated()) // El resto de rutas necesitaran autenticación.
            .sessionManagement(management -> management
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Inhabilita las sesiones.
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
   }
}
