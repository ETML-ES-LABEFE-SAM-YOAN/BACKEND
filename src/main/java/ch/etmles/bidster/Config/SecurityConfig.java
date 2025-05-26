package ch.etmles.bidster.Config;

import ch.etmles.bidster.Utilisateur.UtilisateurEntity;
import ch.etmles.bidster.Utilisateur.UtilisateurNotFoundException;
import ch.etmles.bidster.Utilisateur.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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


    /* Une fois le système login en place
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
    }*/

    // Création d'un compte démo
    @Bean
    public CommandLineRunner createDemoUser(UtilisateurRepository utilisateurRepository, PasswordEncoder encoder) {
        return args -> {
            if (utilisateurRepository.findByNomUtilisateur("demo").isEmpty()) {
                UtilisateurEntity demo = new UtilisateurEntity();
                demo.setNomUtilisateur("demo");
                demo.setNom("Démo");
                demo.setPrenom("Utilisateur");
                demo.setEmail("demo@email.com");
                demo.setMotDePasse(encoder.encode("demo"));
                demo.setSolde(1000.0);
                // ... autres champs si besoin
                utilisateurRepository.save(demo);
                System.out.println("Utilisateur démo créé : demo/demo");
            }
        };
    }
    //Connexion du compte démo à mettre en commentaire pour montrer la partie qui fonctionne sans utilisateur
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        System.out.println("--------------------------------------------------");
        System.out.println("Utilisateur de démo en mémoire prêt à l'emploi !");
        System.out.println("Connectez-vous avec : demo / demo");
        System.out.println("--------------------------------------------------");
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("demo")
                        .password(passwordEncoder.encode("demo"))
                        .roles("USER")
                        .build()
        );
    }


}
