package it.unicam.model;

import it.unicam.model.util.ContentGI;
import it.unicam.model.util.ContestGI;
import it.unicam.repositories.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContestManager {

    //private List<Contest> contests = new ArrayList<>();
    @Autowired
    private ContestRepository contestRepository;

    public void addContest(Contest constest){
//        constest.setId(this.contests.size()+1);
//        this.contests.add(constest);
        this.contestRepository.save(constest);
    }

    public Contest getContest(Long id){
       // return this.contests.get(id-1);
        return this.contestRepository.findById(id).get();
    }
    public List<ContestGI> getAllOpenedContestOnInvite() {
        List <ContestGI> contests = new ArrayList<ContestGI>();
         this.contestRepository.findAll().forEach(contest -> {
            if(contest.isOnInvite() && (!contest.isClosed()) && (contest.numUtentiInvitati() == 0)){
                contests.add(contest.getGeneralInfoContest());
            }
        });
        return contests;
       // return this.contests.stream().filter(contest -> contest.isOnInvite() && (!contest.isClosed()) && (contest.numUtentiInvitati() == 0)).map(contest -> contest.getGeneralInfoContest()).toList();
    }

    public List<ContestGI> getAllContest(Long contributorId) {
        List <ContestGI> contests = new ArrayList<ContestGI>();
        this.contestRepository.findAll().forEach(contest -> {
            if(!contest.isClosed() && contest.contributorInvited(contributorId)){
                contests.add(contest.getGeneralInfoContest());
            }
        });
        return contests;
       //return this.contests.stream().filter(contest -> !contest.isClosed() && contest.contributorInvited(contributorId)).map(contest -> contest.getGeneralInfoContest()).toList();
    }
/*
    public List<ContestGI> getAllOpenedContest() {
        return this.contests.stream().filter(contest -> !contest.isClosed()).map(contest -> contest.getGeneralInfoContest()).toList();
    }

    public List<ContestGI> getAllContests() {
        return this.contests.stream().map(contest -> contest.getGeneralInfoContest()).toList();
    }

    public List<ContentGI> viewSelectedContestContents(int contestId) {
        return this.contests.get(contestId-1).getContents();
    }
 */
}
