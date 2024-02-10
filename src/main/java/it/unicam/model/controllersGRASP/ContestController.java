package it.unicam.model.controllersGRASP;

import it.unicam.model.Contest;
import it.unicam.model.ContestManager;

public class ContestController {

    private ContestManager contestManager;
    private Contest lastContest;

    public ContestController(ContestManager contestManager) {
        this.contestManager = contestManager;
    }

    public void insertContestInfo(String name, String objective) {
        this.lastContest = new Contest(name, objective);
        this.lastContest.setId(this.contestManager.contestsSize());
        this.contestManager.addContest(lastContest);
    }

    public void onInvite(boolean flag) {
        this.lastContest.setOnInvite(flag);
    }
}
