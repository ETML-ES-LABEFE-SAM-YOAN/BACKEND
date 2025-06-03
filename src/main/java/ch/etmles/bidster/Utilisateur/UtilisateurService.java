package ch.etmles.bidster.Utilisateur;

import ch.etmles.bidster.Utilisateur.DTO.UtilisateurDTO;
import ch.etmles.bidster.Utilisateur.DTO.UtilisateurInfoDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UtilisateurEntity creerUtilisateur(UtilisateurDTO dto) {
        if (utilisateurRepository.findByNomUtilisateur(dto.nomUtilisateur).isPresent()) {
            throw new UserAlreadyExistsException("Nom d'utilisateur déjà utilisé !");
        }
        if (utilisateurRepository.findByEmail(dto.email).isPresent()) {
            throw new UserAlreadyExistsException("Cette adresse email est déjà utilisée. Veuillez en utiliser une autre ou vous connecter.");
        }

        validerMotDePasse(dto.motDePasse);

        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setNomUtilisateur(dto.nomUtilisateur);
        utilisateur.setNom(dto.nom);
        utilisateur.setPrenom(dto.prenom);
        utilisateur.setEmail(dto.email);
        utilisateur.setMotDePasse(passwordEncoder.encode(dto.motDePasse)); // <-- Hash ici !
        utilisateur.setTelephone(dto.telephone);
        utilisateur.setRue(dto.rue);
        utilisateur.setNpa(dto.npa);
        utilisateur.setLocalite(dto.localite);
        utilisateur.setSolde(0.0);
        utilisateur.setDateCreation(new java.util.Date());

        return utilisateurRepository.save(utilisateur);
    }

    private void validerMotDePasse(String motDePasse) {
        if (motDePasse == null || motDePasse.length() < 8) {
            throw new PasswordInvalidException("Le mot de passe doit contenir au moins 8 caractères.");
        }
        if (!motDePasse.matches(".*[A-Z].*")) {
            throw new PasswordInvalidException("Le mot de passe doit contenir au moins une majuscule.");
        }
        if (!motDePasse.matches(".*[a-z].*")) {
            throw new PasswordInvalidException("Le mot de passe doit contenir au moins une minuscule.");
        }
        if (!motDePasse.matches(".*\\d.*")) {
            throw new PasswordInvalidException("Le mot de passe doit contenir au moins un chiffre.");
        }
        if (!motDePasse.matches(".*[^a-zA-Z0-9].*")) {
            throw new PasswordInvalidException("Le mot de passe doit contenir au moins un caractère spécial.");
        }
    }

    @Transactional
    public UtilisateurInfoDTO ajouterSolde(String nomUtilisateur, Double montant) {
        UtilisateurEntity utilisateur = utilisateurRepository.findById(nomUtilisateur).orElse(null);
        if (utilisateur != null && montant > 0) {
            utilisateur.setSolde(utilisateur.getSolde() + montant);
            utilisateurRepository.save(utilisateur);
            return new UtilisateurInfoDTO(utilisateur);
        }
        return null;
    }

    @Transactional
    public UtilisateurInfoDTO reduireSolde(String nomUtilisateur, Double montant) {
        UtilisateurEntity utilisateur = utilisateurRepository.findById(nomUtilisateur).orElse(null);
        if (utilisateur != null && montant > 0 && utilisateur.getSolde() >= montant) {
            utilisateur.setSolde(utilisateur.getSolde() - montant);
            utilisateurRepository.save(utilisateur);
            return new UtilisateurInfoDTO(utilisateur);
        }
        return null;
    }

    public UtilisateurInfoDTO getUtilisateurParNom(String nomUtilisateur) {
        UtilisateurEntity utilisateur = utilisateurRepository.findById(nomUtilisateur).orElse(null);
        if (utilisateur != null) {
            return new UtilisateurInfoDTO(utilisateur);
        }
        return null;
    }
}
