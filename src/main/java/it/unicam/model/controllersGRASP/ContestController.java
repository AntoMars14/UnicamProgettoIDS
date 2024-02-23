package it.unicam.model.controllersGRASP;

import it.unicam.model.Content;
import it.unicam.model.Contest;
import it.unicam.model.ContestManager;
import it.unicam.model.UtentiUtenticatiManager;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ContestController {

    private ContestManager contestManager;
    private UtentiUtenticatiManager utentiUtenticatiManager;
    private Contest lastContest;
    private UtenteAutenticato lastContributor;
    private Content lastContent;

    public ContestController(ContestManager contestManager, UtentiUtenticatiManager utentiUtenticatiManager) {
        this.contestManager = contestManager;
        this.utentiUtenticatiManager = utentiUtenticatiManager;
    }

    public void createContest(ContestGI c) {
        Contest contest = new Contest(c.getName(), c.getObjective());
        contest.setOnInvite(c.isOnInvite());
        this.contestManager.addContest(contest);
    }

    public void insertContestInfo(String name, String objective) {
        this.lastContest = new Contest(name, objective);
        this.contestManager.addContest(lastContest);
    }

    public void onInvite(boolean flag) {
        this.lastContest.setOnInvite(flag);
    }

    public List<UtenteAutenticatoGI> selectedContestContibutors(int i) {
//        this.lastContest = this.contestManager.getContest(i);
        return this.utentiUtenticatiManager.getAllContributors();
    }

    public void inviteContributor(int i) {
        this.lastContest.inviteContributor(this.utentiUtenticatiManager.getUser(i));

    }

    public void partecipateContest(int id, int contributorId) {
//        this.lastContest = this.contestManager.getContest(id);
        this.lastContributor = this.utentiUtenticatiManager.getUser(contributorId);
    }

//    public void insertContestContentInfo(String name, String desc, File f) {
//        this.lastContent = new Content(name, desc, f,0);
//    }


    /*
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

    public List<ContentGI> selectedContestValidatedContent(int i) {
        this.lastContest = this.contestManager.getContest(i);
        return this.lastContest.getContestContentValidate();
    }

    public void selectedWinnerContent(int i) {
        //codice per inviare email necessita di impostare le credenziali, provato con gmail attivando l'opzione app meno sicure
        //SMTPUtil.sendEmail(SMTPUtil.createSession(), this.lastContest.getAutoreContentEmail(i), "Vincitore contest di contribuzione", "Congratulazioni, sei il vincitore del contest di contribuzione, mostra questa mail per ritirare il premio");
        this.lastContest.closeContest();

    }

    public List<ContentGI> viewSelectedContestContents(int contestId) {
        this.lastContest = this.contestManager.getContest(contestId);
        return this.contestManager.viewSelectedContestContents(contestId);
    }

    public ContentFD viewSelectedContestContent(int contentId) {
        return this.lastContest.viewSelectedContestContent(contentId);
    }
     */
}
