package it.unicam.model;

import it.unicam.model.util.ContentGI;
import it.unicam.model.util.ContestGI;

import java.util.ArrayList;
import java.util.List;

public class ContestManager {

    private List<Contest> contests = new ArrayList<>();

    public void addContest(Contest constest){
        constest.setId(this.contests.size()+1);
        this.contests.add(constest);
    }

    public Contest getContest(int id){
        return this.contests.get(id-1);
    }
    public List<ContestGI> getAllOpenedContestOnInvite() {
        return this.contests.stream().filter(contest -> contest.isOnInvite() && (!contest.isClosed()) && (contest.numUtentiInvitati() == 0)).map(contest -> contest.getGeneralInfoContest()).toList();
    }

    public List<ContestGI> getAllContest(int contributorId) {
       return this.contests.stream().filter(contest -> !contest.isClosed() && contest.contributorInvited(contributorId)).map(contest -> contest.getGeneralInfoContest()).toList();
    }

    public List<ContestGI> getAllOpenedContest() {
        return this.contests.stream().filter(contest -> !contest.isClosed()).map(contest -> contest.getGeneralInfoContest()).toList();
    }

    public List<ContestGI> getAllContests() {
        return this.contests.stream().map(contest -> contest.getGeneralInfoContest()).toList();
    }

    public List<ContentGI> viewSelectedContestContents(int contestId) {
        return this.contests.get(contestId-1).getContents();
    }
}
