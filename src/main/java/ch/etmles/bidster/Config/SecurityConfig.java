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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/utilisateurs/creer",
                                "/lots/sous-categorie/**",
                                "/lots/categorie-principale/**",
                                "/lots/all",
                                "/lots/{id:[0-9]+}",
                                "/categories/principales"
                        ).permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/lots").authenticated()
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
}
