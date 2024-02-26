package it.unicam.model.utenti;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.UtenteAutenticatoGI;
import it.unicam.repositories.UtenteAutenticatoRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.hibernate.engine.internal.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UtentiAutenticatiManager {

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;
    @OneToMany
    private List<UtenteAutenticato> utenti = new ArrayList<>();
    @OneToMany
    private List<UtenteAutenticato> registrazioniUtenti = new ArrayList<>();

    public void addUtente(UtenteAutenticato utenteAutenticato) {
        this.utenti.add(utenteAutenticato);
    }
    public UtenteAutenticato getUser(Long id){
        return this.utenti.stream().filter(u -> u.getId().equals(id)).findFirst().get();
    }
    public List<UtenteAutenticatoGI> getAllContributors() {
        return this.utenti.stream().filter(u -> u.getRole().equals(Role.CONTRIBUTOR) || u.getRole().equals(Role.CONTRIBUTORAUTORIZZATO) || u.getRole().equals(Role.CURATORE)).map(UtenteAutenticato::getGeneralInfoUtenteAutenticato).toList();
    }

    public UtenteAutenticatoGI getUserGI(Long id) {
        return this.utenteAutenticatoRepository.findById(id).get().getGeneralInfoUtenteAutenticato();
    }

    public List<UtenteAutenticatoGI> viewAllUsers() {
        return this.utenti.stream().filter(u -> !u.getRole().equals(Role.GESTORE)).map(UtenteAutenticato::getGeneralInfoUtenteAutenticato).toList();
    }

    public void changeRole(Long id, Role role) {
        UtenteAutenticato utente = this.utenti.stream().filter(u -> u.getId().equals(id)).findFirst().get();
        utente.setRole(role);
        this.utenteAutenticatoRepository.save(utente);
    }

    public void addRegistrationUser(UtenteAutenticato user) {
        this.registrazioniUtenti.add(user);
        this.utenteAutenticatoRepository.save(user);
    }

    public List<UtenteAutenticatoGI> viewRegistrationUsers() {
        return this.registrazioniUtenti.stream().map(UtenteAutenticato::getGeneralInfoUtenteAutenticato).toList();
    }


    public void approveRegistration(Long id) {
        this.utenti.add(this.registrazioniUtenti.stream().filter(u -> u.getId().equals(id)).findFirst().get());
        this.registrazioniUtenti.removeIf(u -> u.getId().equals(id));
    }

    public void refuseRegistration(Long id) {
        this.registrazioniUtenti.removeIf(u -> u.getId().equals(id));
        this.utenteAutenticatoRepository.deleteById(id);
    }

    public boolean containsUser(String email, String username) {
        return (this.utenti.stream().anyMatch(u -> u.getEmail().equals(email) || u.getUsername().equals(username)) || this.registrazioniUtenti.stream().anyMatch(u -> u.getEmail().equals(email) || u.getUsername().equals(username)));
    }
}
