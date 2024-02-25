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
    public void addUtente(UtenteAutenticato utente){
        //utente.setId(this.utenti.size()+1);
        this.utenti.add(utente);
    }

    public UtenteAutenticato getUser(Long id){
        //return this.utenti.get(id-1);
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
//        return this.utenti.get(id -1).getGeneralInfoUtenteAutenticato();
        return this.utenteAutenticatoRepository.findById(id).get().getGeneralInfoUtenteAutenticato();

    }

    public List<UtenteAutenticatoGI> viewAllUsers() {
        List<UtenteAutenticatoGI> utenti = new ArrayList<>();
        this.utenteAutenticatoRepository.findAll().forEach(utente -> utenti.add(utente.getGeneralInfoUtenteAutenticato()));
        return utenti;
       // return this.utenti.stream().filter(u-> !u.getRole().equals(Role.GESTORE)).map(UtenteAutenticato::getGeneralInfoUtenteAutenticato).toList();
    }

    public void changeRole(Long id, Role role) {
        UtenteAutenticato utente = this.utenteAutenticatoRepository.findById(id).get();
        utente.setRole(role);
        this.utenteAutenticatoRepository.save(utente);
        //this.utenti.get(id-1).setRole(role);
    }

    public void addRegistrationUser(UtenteAutenticato lastUser) {
        this.utenteAutenticatoRepository.save(lastUser);
        //lastUser.setId(this.registrazioniUtenti.size()+1);
        this.registrazioniUtenti.add(lastUser);
    }

    public List<UtenteAutenticatoGI> viewRegistrationUsers() {
        return this.registrazioniUtenti.stream().map(UtenteAutenticato::getGeneralInfoUtenteAutenticato).toList();
    }

    public UtenteAutenticato getRegistrationUser(int i) {
        return this.registrazioniUtenti.get(i-1);
    }

    public void approveRegistration(int id) {
        this.utenti.add(this.registrazioniUtenti.get(id-1));
        this.registrazioniUtenti.remove(id-1);
    }

    public void refuseRegistration(int id) {
        this.registrazioniUtenti.remove(id-1);
    }

    public boolean containsUser(String email, String username) {
        return (this.utenti.stream().anyMatch(u -> u.getEmail().equals(email) || u.getUsername().equals(username)) || this.registrazioniUtenti.stream().anyMatch(u -> u.getEmail().equals(email) || u.getUsername().equals(username)));
    }
}
