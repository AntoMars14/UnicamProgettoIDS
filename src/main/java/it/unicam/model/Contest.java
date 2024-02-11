package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.ContestGI;
import it.unicam.model.util.UtenteAutenticatoGI;

import java.util.ArrayList;
import java.util.List;

public class Contest {

    private int id;
    private String name;
    private String objective;
    private boolean onInvite;
    private boolean isClosed;
    private List<UtenteAutenticato> invitedUsers;

    public Contest(String name, String objective) {
        this.name = name;
        this.objective = objective;
        this.isClosed = false;
        this.invitedUsers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public boolean isOnInvite() {
        return onInvite;
    }

    public void setOnInvite(boolean onInvite) {
        this.onInvite = onInvite;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public int numUtentiInvitati() {
        return this.invitedUsers.size();
    }

    public ContestGI getGeneralInfoContest() {
        return new ContestGI(this.id, this.name, this.objective, this.isClosed);
    }

    public void inviteContributor(UtenteAutenticato user) {
        this.invitedUsers.add(user);
    }
}
