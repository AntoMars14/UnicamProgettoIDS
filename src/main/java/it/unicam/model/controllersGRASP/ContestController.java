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


    public void createContest(ContestGI c) {
        Contest contest = new Contest(c.getName(), c.getObjective());
        contest.setOnInvite(c.isOnInvite());
        this.contestManager.addContest(contest);
    }


    public List<UtenteAutenticatoGI> selectedContestContibutors() {
        return this.utentiUtenticatiManager.getAllContributors();
    }

    public void inviteContributor(Long id, Long idContributor) {
        Contest contest = this.contestRepository.findById(id).get();
        contest.inviteContributor(this.utentiUtenticatiManager.getUser(idContributor));
        this.contestRepository.save(contest);
    }

    public void partecipateContest(Long id, ContentFD content, Long contributorId) {
        Content c = new Content(content.getNome(), content.getDescrizione(), content.getFile());
        Contest contest = this.contestRepository.findById(id).get();
        contest.addContent(c, this.utentiUtenticatiManager.getUser(contributorId));
        this.contestRepository.save(contest);
    }


    public List<ContentGI> viewPendingContentContest(Long i) {
        Contest contest = this.contestRepository.findById(i).get();
        return contest.getContestContentPending();
    }

    public ContentFD selectedContestContent(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        return contest.selectedContestContent(id).getFullDetailedContent();
    }

    public void deleteContestContent(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        contest.deleteContestContent(contest.selectedContestContent(id));
        this.contestRepository.save(contest);
    }

    public void validateContestC(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        contest.validateContestC(contest.selectedContestContent(id));
        this.contestRepository.save(contest);
    }

    public void selectedWinnerContent(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        contest.closeContest();
        this.contestRepository.save(contest);
        //codice per inviare email necessita di impostare le credenziali, provato con gmail attivando l'opzione app meno sicure
        //SMTPUtil.sendEmail(SMTPUtil.createSession(), this.lastContest.getAutoreContentEmail(i), "Vincitore contest di contribuzione", "Congratulazioni, sei il vincitore del contest di contribuzione, mostra questa mail per ritirare il premio");
    }

    public List<ContentGI> viewSelectedContestContents(Long contestId) {
        return this.contestManager.viewSelectedContestContents(contestId);
    }

    public ContentFD viewSelectedContestContent(Long contestId, Long id) {
        Contest contest = this.contestRepository.findById(contestId).get();
        return contest.viewSelectedContestContent(id);
    }

}
