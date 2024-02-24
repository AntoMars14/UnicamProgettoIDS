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

    @Autowired
    private ContestRepository contestRepository;

    public void addContest(Contest constest){
        this.contestRepository.save(constest);
    }

    public Contest getContest(Long id){
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
    }

    public List<ContestGI> getAllContest(Long contributorId) {
        List <ContestGI> contests = new ArrayList<ContestGI>();
        this.contestRepository.findAll().forEach(contest -> {
            if((!contest.isClosed()) && contest.contributorInvited(contributorId)){
                contests.add(contest.getGeneralInfoContest());
            }
        });
        return contests;
    }

    public List<ContestGI> getAllOpenedContest() {
        List <ContestGI> contests = new ArrayList<ContestGI>();
        this.contestRepository.findAll().forEach(contest -> {
            if(!contest.isClosed()){
                contests.add(contest.getGeneralInfoContest());
            }
        });
        return contests;
    }


    public List<ContestGI> getAllContests() {
        List <ContestGI> contests = new ArrayList<ContestGI>();
        this.contestRepository.findAll().forEach(contest -> {
            contest.getGeneralInfoContest();
            contests.add(contest.getGeneralInfoContest());
        });
        return contests;
    }

    public List<ContentGI> viewSelectedContestContents(Long contestId) {
        return this.contestRepository.findById(contestId).get().getContestContentValidate();
    }

}
