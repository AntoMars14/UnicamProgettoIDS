package it.unicam.model;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.UtenteAutenticatoGI;
import it.unicam.repositories.RichiestaRuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleManager {
//    private List<Integer> changeRoleIDs;
    @Autowired
    private RichiestaRuoloRepository richiestaRuoloRepository;
    private UtentiUtenticatiManager utentiAutenticatiManager;

    public RoleManager(UtentiUtenticatiManager utentiAutenticatiManager) {
        this.utentiAutenticatiManager = utentiAutenticatiManager;
//        this.changeRoleIDs = new ArrayList<>();
    }

    public void requestChangeRole(Long id) {
        this.richiestaRuoloRepository.save(new RichiestaRuolo(id));
//        this.changeRoleIDs.add(id);
    }

    public List<UtenteAutenticatoGI> viewChangeRoleRequests() {
//        return this.changeRoleIDs.stream().map(id -> this.utentiAutenticatiManager.getUserGI(id)).toList();
        List<UtenteAutenticatoGI> utenti = new ArrayList<>();
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> utenti.add(this.utentiAutenticatiManager.getUserGI(richiestaRuolo.getIdUtente())));
        return utenti;
    }

    public void disapproveRequest(Long id) {
//        this.changeRoleIDs.remove(Integer.valueOf(id));
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
        this.utentiAutenticatiManager.addUtente(u);
        this.richiestaRuoloRepository.findAll().forEach(richiestaRuolo -> {
            if(richiestaRuolo.getIdUtente().equals(id))
                this.richiestaRuoloRepository.delete(richiestaRuolo);
        });
    }
}
