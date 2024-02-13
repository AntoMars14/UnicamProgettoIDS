package it.unicam.model;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.UtenteAutenticatoGI;

import java.util.ArrayList;
import java.util.List;

public class UtentiUtenticatiManager {

    private List<UtenteAutenticato> utenti = new ArrayList<>();

    public void addUtente(UtenteAutenticato utente){
        utente.setId(this.utenti.size()+1);
        this.utenti.add(utente);
    }

    public UtenteAutenticato getUser(int id){
        return this.utenti.get(id-1);
    }
    public List<UtenteAutenticatoGI> getAllContributors() {
        return this.utenti.stream()
                .filter(u -> u.getRole().equals(Role.CONTRIBUTOR) || u.getRole().equals(Role.CONTRIBUTORAUTORIZZATO) || u.getRole().equals(Role.CURATORE))
                .map(UtenteAutenticato::getGeneralInfoUtenteAutenticato).toList();
    }
}
