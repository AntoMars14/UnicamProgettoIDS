package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.ContentGI;
import it.unicam.model.util.ContestGI;

import java.util.*;

public class Contest {

    private int id;
    private String name;
    private String objective;
    private boolean onInvite;
    private boolean isClosed;
    private List<UtenteAutenticato> invitedUsers;
    private Map<Content, UtenteAutenticato> partecipations;
    private Map<Content, UtenteAutenticato> validatedPartecipations;

    public Contest(String name, String objective) {
        this.name = name;
        this.objective = objective;
        this.isClosed = false;
        this.invitedUsers = new ArrayList<>();
        this.partecipations = new HashMap<>();
        this.validatedPartecipations = new HashMap<>();
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
                    return !this.partecipations.containsValue(u);
                }
            }
            return false;
        }else {
            for(UtenteAutenticato u : this.partecipations.values()){
                if (u.getId() == contributorId) {
                    return false;
                }
            }
            return true;
        }
    }

    public void addContent(Content content, UtenteAutenticato contributor) {
        //content.setContentId(this.partecipations.size() + 1);
        this.partecipations.put(content, contributor);
    }

    public List<ContentGI> getContestContentPending() {
        return this.partecipations.entrySet().stream().map(entry -> entry.getKey().getContentGeneralInfo()).toList();
    }

    public Content selectedContestContent(int i) {
        return this.partecipations.entrySet().stream().filter(entry -> entry.getKey().getContentId() == i).map(entry -> entry.getKey()).findFirst().orElse(null);
    }

    public void deleteContestContent(Content content) {
        //this.partecipations.keySet().stream().filter(c -> c.getContentId() > content.getContentId()).forEach(c -> c.setContentId(c.getContentId() - 1));
        this.partecipations.remove(content);
    }

    public void validateContestC(Content content) {
        //content.setContentId(this.validatedPartecipations.size() + 1);
        this.validatedPartecipations.put(content, this.partecipations.get(content));
        this.deleteContestContent(content);
    }

    public List<ContentGI> getContestContentValidate() {
        return this.validatedPartecipations.keySet().stream().map(c -> c.getContentGeneralInfo()).toList();
    }

    public String getAutoreContentEmail(int i) {
        return this.validatedPartecipations.entrySet().stream().filter(entry -> entry.getKey().getContentId() == i).map(entry -> entry.getValue().getEmail()).findFirst().orElse(null);
    }

    public void closeContest() {
        this.isClosed = true;
    }

    public List<ContentGI> getContents() {
        return this.validatedPartecipations.keySet().stream().map(c -> c.getContentGeneralInfo()).toList();
    }

    public ContentFD viewSelectedContestContent(int contentId) {
        return this.validatedPartecipations.keySet().stream().filter(c -> c.getContentId() == contentId).map(c -> c.getFullDetailedContent()).findFirst().orElse(null);
    }
}
