package it.unicam.model;

import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.dtos.ContentFD;
import it.unicam.model.util.dtos.ContentGI;
import it.unicam.model.util.dtos.ContestGI;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contest_generator")
    private Long id;
    private String name;
    private String objective;
    private boolean onInvite;
    private boolean isClosed;
    @ManyToMany
    private List<UtenteAutenticato> invitedUsers = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Partecipation> partecipations = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Partecipation> validatedPartecipations = new ArrayList<>();

    public Contest(String name, String objective) {
        this.name = name;
        this.objective = objective;
        this.isClosed = false;
        this.invitedUsers = new ArrayList<>();
    }

    public Contest() {

    }

    public Long getId() {
        return id;
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
        return new ContestGI(this.id, this.name, this.objective, this.onInvite, this.isClosed);
    }

    public void inviteContributor(UtenteAutenticato user) {
        this.invitedUsers.add(user);
    }


    public boolean contributorInvited(Long contributorId) {
        if (isOnInvite()){
            for (UtenteAutenticato u : this.invitedUsers) {
                if (u.getId() == contributorId) {
                    return (this.partecipations.stream().filter(p -> p.getUser().getId().equals(contributorId)).toList().size() == 0
                            && this.validatedPartecipations.stream().filter(p -> p.getUser().getId().equals(contributorId)).toList().size() == 0);
                }
            }
            return false;
        }else {
            return (this.partecipations.stream().filter(p -> p.getUser().getId().equals(contributorId)).toList().size() == 0
                    && this.validatedPartecipations.stream().filter(p -> p.getUser().getId().equals(contributorId)).toList().size() == 0);
        }
    }

    public void addContent(Content content, UtenteAutenticato contributor) {
        this.partecipations.add(new Partecipation(content, contributor));
    }
    public List<ContentGI> getContestContentPending() {
        return this.partecipations.stream().map(p -> p.getContent().getContentGeneralInfo()).toList();
    }

    public Content selectedContestContent(Long i) {
        return this.partecipations.stream().filter(p -> p.getContent().getContentId().equals(i)).map(p -> p.getContent()).findFirst().orElse(null);
    }

    public void deleteContestContent(Content content) {
        this.partecipations.remove(content);
    }

    public void validateContestC(Content content) {
        this.validatedPartecipations.add(this.partecipations.stream().filter(p -> p.getContent().equals(content)).findFirst().orElse(null));
        this.partecipations.removeIf(p -> p.getContent().equals(content));
    }
    public List<ContentGI> getContestContentValidate() {
       return this.validatedPartecipations.stream().map(p -> p.getContent().getContentGeneralInfo()).toList();
    }

    public String getAutoreContentEmail(Long id) {
        return this.partecipations.stream().filter(p -> p.getContent().getContentId().equals(id)).map(p -> p.getUser().getEmail()).findFirst().orElse(null);
    }

    public void closeContest() {
        this.isClosed = true;
    }

    public ContentFD viewSelectedContestContent(Long contentId) {
        if(this.validatedPartecipations.stream().filter(p -> p.getContent().getContentId().equals(contentId)).toList().size() == 0)
            return null;
        else
            return this.validatedPartecipations.stream().filter(p -> p.getContent().getContentId().equals(contentId)).map(p -> p.getContent().getFullDetailedContent()).findFirst().orElse(null);
    }

}
