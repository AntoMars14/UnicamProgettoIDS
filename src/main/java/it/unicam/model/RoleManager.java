package it.unicam.model;

import it.unicam.model.utenti.Role;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.UtenteAutenticatoGI;

import java.util.ArrayList;
import java.util.List;

public class RoleManager {
    private List<Integer> changeRoleIDs;
    private UtentiUtenticatiManager utentiAutenticatiManager;

    public RoleManager(UtentiUtenticatiManager utentiAutenticatiManager) {
        this.utentiAutenticatiManager = utentiAutenticatiManager;
        this.changeRoleIDs = new ArrayList<>();
    }
    public void requestChangeRole(int id) {
        this.changeRoleIDs.add(id);
    }

    public List<UtenteAutenticatoGI> viewChangeRoleRequests() {
        return this.changeRoleIDs.stream().map(id -> this.utentiAutenticatiManager.getUserGI(id)).toList();
    }

    public void disapproveRequest(int id) {
        this.changeRoleIDs.remove(Integer.valueOf(id));
    }

    public void approveRequest(int id) {
        UtenteAutenticato u = this.utentiAutenticatiManager.getUser(id);
        switch (u.getRole()){
            case CONTRIBUTOR -> u.setRole(Role.CONTRIBUTORAUTORIZZATO);
            case TURISTAUTENTICATO -> u.setRole(Role.CONTRIBUTOR);
            default -> throw new IllegalArgumentException("Unexpected value: " + u.getRole());
        }
        this.changeRoleIDs.remove(Integer.valueOf(id));
    }
}
