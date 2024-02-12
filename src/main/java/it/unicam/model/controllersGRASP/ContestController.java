package it.unicam.model.controllersGRASP;

import it.unicam.model.Content;
import it.unicam.model.Contest;
import it.unicam.model.ContestManager;
import it.unicam.model.UtentiUtenticatiManager;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.ContentFD;
import it.unicam.model.util.ContentGI;
import it.unicam.model.util.UtenteAutenticatoGI;

import java.io.File;
import java.util.List;

public class ContestController {

    private ContestManager contestManager;
    private UtentiUtenticatiManager utentiUtenticatiManager;
    private Contest lastContest;
    private UtenteAutenticato lastContributor;
    private Content lastContent;
    private ContentFD lastContentViewed;

    public ContestController(ContestManager contestManager, UtentiUtenticatiManager utentiUtenticatiManager) {
        this.contestManager = contestManager;
        this.utentiUtenticatiManager = utentiUtenticatiManager;
    }

    public void insertContestInfo(String name, String objective) {
        this.lastContest = new Contest(name, objective);
        this.contestManager.addContest(lastContest);
    }

    public void onInvite(boolean flag) {
        this.lastContest.setOnInvite(flag);
    }

    public List<UtenteAutenticatoGI> selectedContestContibutors(int i) {
        this.lastContest = this.contestManager.getContest(i);
        return this.utentiUtenticatiManager.getAllContributors();
    }

    public void inviteContributor(int i) {
        this.lastContest.inviteContributor(this.utentiUtenticatiManager.getUser(i));

    }

    public void partecipateContest(int id, int contributorId) {
        this.lastContest = this.contestManager.getContest(id);
        this.lastContributor = this.utentiUtenticatiManager.getUser(contributorId);
    }

    public void insertContestContentInfo(String name, String desc, File f) {
        this.lastContent = new Content(name, desc, f,0);
    }

    public void confirmPartecipation() {
        this.lastContest.addContent(this.lastContent, this.lastContributor);
    }

    public List<ContentGI> viewPendingContentContest(int i) {
        this.lastContest = this.contestManager.getContest(i);
        return this.lastContest.getContestContentPending();
    }

    public ContentFD selectedContestContent(int i) {
        this.lastContent = this.lastContest.selectedContestContent(i);
        return this.lastContent.getFullDetailedContent();
    }

    public void deleteContestContent() {
        this.lastContest.deleteContestContent(this.lastContent);
    }

    public void validateContestC() {
        this.lastContest.validateContestC(this.lastContent);
    }
}
