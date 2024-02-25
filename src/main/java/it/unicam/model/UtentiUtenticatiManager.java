package it.unicam.model;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.UtenteAutenticatoGI;
import it.unicam.repositories.UtenteAutenticatoRepository;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UtentiUtenticatiManager {

    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;
    private List<UtenteAutenticato> utenti = new ArrayList<>();
    @OneToMany
    private List<UtenteAutenticato> registrazioniUtenti = new ArrayList<>();

    public void addUtente(UtenteAutenticato utenteAutenticato) {
        this.utenti.add(utenteAutenticato);
    }
    public UtenteAutenticato getUser(Long id){
        return this.utenteAutenticatoRepository.findById(id).get();
    }
    public List<UtenteAutenticatoGI> getAllContributors() {
        List<UtenteAutenticatoGI> contributors = new ArrayList<>();
        this.utenteAutenticatoRepository.findAll().forEach(utente -> {
            if(utente.getRole().equals(Role.CONTRIBUTOR) || utente.getRole().equals(Role.CONTRIBUTORAUTORIZZATO) || utente.getRole().equals(Role.CURATORE))
                contributors.add(utente.getGeneralInfoUtenteAutenticato());
        });
        return contributors;
    }

    public UtenteAutenticatoGI getUserGI(Long id) {
        return this.utenteAutenticatoRepository.findById(id).get().getGeneralInfoUtenteAutenticato();
    }

    public List<UtenteAutenticatoGI> viewAllUsers() {
        return this.utenti.stream().filter(u -> !u.getRole().equals(Role.GESTORE)).map(UtenteAutenticato::getGeneralInfoUtenteAutenticato).toList();
    }

    public void changeRole(Long id, Role role) {
        UtenteAutenticato utente = this.utenteAutenticatoRepository.findById(id).get();
        utente.setRole(role);
        this.utenteAutenticatoRepository.save(utente);
    }

    public void addRegistrationUser(UtenteAutenticato user) {
        this.utenteAutenticatoRepository.save(user);
        this.registrazioniUtenti.add(user);
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
