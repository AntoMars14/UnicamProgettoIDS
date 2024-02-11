package it.unicam.model.controllersGRASP;

import it.unicam.model.Contest;
import it.unicam.model.ContestManager;
import it.unicam.model.UtentiUtenticatiManager;
import it.unicam.model.util.UtenteAutenticatoGI;

import java.util.List;

public class ContestController {

    private ContestManager contestManager;
    private UtentiUtenticatiManager utentiUtenticatiManager;
    private Contest lastContest;

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
}
