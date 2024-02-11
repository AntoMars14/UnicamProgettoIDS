package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.ContestGI;
import it.unicam.model.util.UtenteAutenticatoGI;

import java.util.*;

public class Contest {

    private int id;
    private String name;
    private String objective;
    private boolean onInvite;
    private boolean isClosed;
    private List<UtenteAutenticato> invitedUsers;
    private Map<UtenteAutenticato, Content> partecipations;

    public Contest(String name, String objective) {
        this.name = name;
        this.objective = objective;
        this.isClosed = false;
        this.invitedUsers = new ArrayList<>();
        this.partecipations = new HashMap<>();
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

    public boolean contributorInvited(int contributorId) {
        if (isOnInvite()){
            for (UtenteAutenticato u : this.invitedUsers) {
                if (u.getId() == contributorId) {
                    return !this.partecipations.containsKey(u);
                }
            }
            return false;
        }else {
            for(UtenteAutenticato u : this.partecipations.keySet()){
                if (u.getId() == contributorId) {
                    return false;
                }
            }
            return true;
        }
    }

    public void addContent(Content lastContent, UtenteAutenticato lastContributor) {
        this.partecipations.put(lastContributor, lastContent);
    }
}
