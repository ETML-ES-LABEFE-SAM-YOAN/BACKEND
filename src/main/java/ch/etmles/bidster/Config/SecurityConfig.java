package ch.etmles.bidster.Config;

import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurNotFoundException;
import ch.etmles.bidster.Utilisateur.UtilisateurRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publics (accessibles à tous)
                        .requestMatchers(
                                "/utilisateurs/creer",
                                "/lots/sous-categorie/**",
                                "/lots/categorie-principale/**",
                                "/lots/all",
                                "/lots/{id:[0-9]+}",
                                "/categories/principales",
                                "/encheres/lot/*/meilleure",
                                "/encheres/lot/*"
                        ).permitAll()
                        // Endpoints enchères réservés aux utilisateurs connectés
                        .requestMatchers("/encheres/placer").authenticated()
                        .requestMatchers("/encheres/utilisateur/**").authenticated()
                        // Création d’un lot réservée aux connectés
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/lots").authenticated()
                        // Tout le reste est public
                        .anyRequest().permitAll()
                )
                .httpBasic();
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(UtilisateurRepository utilisateurRepository) {
        return username -> {
            UtilisateurEntity utilisateur = utilisateurRepository.findByNomUtilisateur(username)
                    .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur non trouvé"));
            return User.builder()
                    .username(utilisateur.getNomUtilisateur())
                    .password(utilisateur.getMotDePasse()) // hashé !
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173/"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // Autorise l’envoi des cookies (si besoin)
        configuration.setMaxAge(3600L); // Durée de validité de la pré-vérification

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
