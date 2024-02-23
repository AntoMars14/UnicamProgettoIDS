package it.unicam.model.controllersGRASP;

import it.unicam.model.*;
import it.unicam.model.utenti.UtenteAutenticato;
import it.unicam.model.util.*;
import it.unicam.repositories.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestController {
    @Autowired
    private ContestManager contestManager;
    @Autowired
    private UtentiUtenticatiManager utentiUtenticatiManager;
    @Autowired
    private ContestRepository contestRepository;
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

    public void inviteContributor(Long id, Long idContributor) {
        this.contestManager.getContest(id).inviteContributor(this.utentiUtenticatiManager.getUser(idContributor));
      //  this.lastContest.inviteContributor(this.utentiUtenticatiManager.getUser(i));

    }

    public void partecipateContest(Long id, ContentFD content, Long contributorId) {
        Content c = new Content(content.getNome(), content.getDescrizione(), content.getFile());
        Contest contest = this.contestRepository.findById(id).get();
        contest.addContent(c, this.utentiUtenticatiManager.getUser(contributorId));
        this.contestRepository.save(contest);

//        this.lastContest = this.contestManager.getContest(id);
       // this.lastContributor = this.utentiUtenticatiManager.getUser(contributorId);
    }

//    public void insertContestContentInfo(String name, String desc, File f) {
//        this.lastContent = new Content(name, desc, f,0);
//    }


    /*
    public void confirmPartecipation() {
        this.lastContest.addContent(this.lastContent, this.lastContributor);
    }
    */

    public List<ContentGI> viewPendingContentContest(Long i) {
        Contest contest = this.contestRepository.findById(i).get();
        return contest.getContestContentPending();
//        this.lastContest = this.contestManager.getContest(i);
//        return this.lastContest.getContestContentPending();
    }

    public ContentFD selectedContestContent(Long i) {
        Contest contest = this.contestRepository.findById(i).get();
        return contest.selectedContestContent(i).getFullDetailedContent();
//        this.lastContent = this.lastContest.selectedContestContent(i);
//        return this.lastContent.getFullDetailedContent();
    }

    public void deleteContestContent(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        contest.deleteContestContent(contest.selectedContestContent(id));
        this.contestRepository.save(contest);
//        this.lastContest.deleteContestContent(this.lastContent);
    }

    public void validateContestC(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        contest.validateContestC(contest.selectedContestContent(id));
        this.contestRepository.save(contest);

     //   this.lastContest.validateContestC(this.lastContent);
    }

    public List<ContentGI> selectedContestValidatedContent(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        return contest.getContestContentValidate();

       // this.lastContest = this.contestManager.getContest(i);
       // return this.lastContest.getContestContentValidate();
    }

    public void selectedWinnerContent(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        contest.closeContest();
        //codice per inviare email necessita di impostare le credenziali, provato con gmail attivando l'opzione app meno sicure
        //SMTPUtil.sendEmail(SMTPUtil.createSession(), this.lastContest.getAutoreContentEmail(i), "Vincitore contest di contribuzione", "Congratulazioni, sei il vincitore del contest di contribuzione, mostra questa mail per ritirare il premio");
      //  this.lastContest.closeContest();

    }
/*
    public List<ContentGI> viewSelectedContestContents(int contestId) {
        this.lastContest = this.contestManager.getContest(contestId);
        return this.contestManager.viewSelectedContestContents(contestId);
    }

    public ContentFD viewSelectedContestContent(int contentId) {
        return this.lastContest.viewSelectedContestContent(contentId);
    }
     */
}
