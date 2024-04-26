package com.platformtest.app.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

/**
 * Esta classe é responsável por configurar as configurações de segurança para a aplicação.
 * Ela utiliza o Spring Security para fornecer serviços de autenticação e autorização.
 * A anotação @EnableWebSecurity habilita as funcionalidades de segurança do web no Spring Security.
 * A anotação @Configuration marca esta classe como uma classe de configuração.
 *
 * @author  [Seu Nome]
 * @version  [1.0.0]
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Value("${jwt.public.key}")
	private RSAPublicKey publicKey;
	@Value("${jwt.private.key}")
	private RSAPrivateKey privateKey;

    /**
     * Este método é responsável por configurar as configurações de segurança para a aplicação.
     * Ele configura a filtro de segurança, habilitando autenticação HTTP básica e servidor de recursos OAuth2.
     * O objeto HttpSecurity é usado para configurar as configurações de segurança.
     *
     * @param http o objeto HttpSecurity usado para configurar as configurações de segurança
     * @return o filtro de segurança configurado
     * @throws Exception se ocorrer um erro durante a configuração
     */
	@Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        	http
        		.cors(custom -> custom.configurationSource(corsConfigurationSource()))
        		.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(custom -> {
                	custom.requestMatchers(HttpMethod.POST, "/api/register/**").permitAll();
                	custom.requestMatchers(HttpMethod.GET, "/api/register/*").permitAll();
                    custom.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(Customizer.withDefaults());
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
                
                return http.build();
    }
	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Allow your React application domain
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Allow cookies and authorization headers to be sent along with the request
        config.addAllowedOriginPattern("*"); // Adjust this to match the domain of your React app for better security
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all methods
        source.registerCorsConfiguration("/**", config); // Apply this configuration to all paths
        return source;
    }

    /**
     * Este método é responsável por criar um decodificador de JWT.
     * Ele usa a chave pública fornecida para decodificar tokens JWT.
     *
     * @return um decodificador de JWT
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    /**
     * Este método é responsável por criar um codificador de JWT.
     * Ele usa as chaves privada e pública fornecidas para codificar tokens JWT.
     *
     * @return um codificador de JWT
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}