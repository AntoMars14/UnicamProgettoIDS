package it.unicam.model;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.UtenteAutenticatoGI;
import it.unicam.repositories.RichiestaRuoloRepository;
import it.unicam.repositories.UtenteAutenticatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleManager {

    @Autowired
    private RichiestaRuoloRepository richiestaRuoloRepository;
    @Autowired
    private UtentiUtenticatiManager utentiAutenticatiManager;
    @Autowired
    private UtenteAutenticatoRepository utenteAutenticatoRepository;

    public RoleManager() {
    }

    public void requestChangeRole(Long id) {
        this.richiestaRuoloRepository.save(new RichiestaRuolo(id));
    }

    public List<UtenteAutenticatoGI> viewChangeRoleRequests() {
        List<UtenteAutenticatoGI> utenti = new ArrayList<>();
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> utenti.add(this.utentiAutenticatiManager.getUserGI(richiestaRuolo.getIdUtente())));
        return utenti;
    }

    public void disapproveRequest(Long id) {
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> {
            if(richiestaRuolo.getIdUtente().equals(id))
                this.richiestaRuoloRepository.delete(richiestaRuolo);
        });
    }

    public void approveRequest(Long id) {
    UtenteAutenticato u = this.utentiAutenticatiManager.getUser(id);
        switch (u.getRole()){
            case CONTRIBUTOR -> u.setRole(Role.CONTRIBUTORAUTORIZZATO);
            case TURISTAUTENTICATO -> u.setRole(Role.CONTRIBUTOR);
            default -> throw new IllegalArgumentException("Unexpected value: " + u.getRole());
        }
        this.utenteAutenticatoRepository.save(u);
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> {
            if(richiestaRuolo.getIdUtente().equals(id))
                this.richiestaRuoloRepository.delete(richiestaRuolo);
        });
    }
}
