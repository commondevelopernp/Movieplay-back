package api.movieplay.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.security.PermitAll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Configura la seguridad de la aplicación.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
	public class SecurityConfig {


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(authorize) -> authorize.anyRequest().authenticated())
				.cors()
				.and()
				.csrf().disable()
				.addFilterBefore(jwtAuth(), UsernamePasswordAuthenticationFilter.class)
				
;

	return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers("auth/login","swagger-ui","/api/**").anyRequest()
				;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://movieplay-back-production-2979.up.railway.app/") // Cambia esto al origen de tu frontend
						.allowedMethods("GET", "POST", "PUT", "DELETE") // Especifica los métodos permitidos
						.allowCredentials(true);
			}
		};
	}

	@Bean
	public JwtAuthFilter jwtAuth() {
		return new JwtAuthFilter(secretKey());
	}

	@Bean
	public SecretKey secretKey() {
		SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		return secretKey;
	}
}
