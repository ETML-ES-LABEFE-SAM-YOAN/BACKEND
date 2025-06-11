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
        if (utilisateurRepository.findByNomUtilisateur(dto.getNomUtilisateur()).isPresent()) {
            throw new UserAlreadyExistsException("Nom d'utilisateur déjà utilisé !");
        }
        if (utilisateurRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Cette adresse email est déjà utilisée. Veuillez en utiliser une autre ou vous connecter.");
        }

        validerMotDePasse(dto.getMotDePasse());

        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setNomUtilisateur(dto.getNomUtilisateur());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse())); // <-- Hash ici !
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setRue(dto.getRue());
        utilisateur.setNpa(dto.getNpa());
        utilisateur.setLocalite(dto.getLocalite());
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

    public UtilisateurEntity updateUtilisateur(String nomUtilisateur, UtilisateurInfoDTO dto) {
        UtilisateurEntity utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur non trouvé"));
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setRue(dto.getRue());
        utilisateur.setNpa(dto.getNpa());
        utilisateur.setLocalite(dto.getLocalite());
        return utilisateurRepository.save(utilisateur);
    }

}
