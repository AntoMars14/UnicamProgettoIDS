package it.unicam.model;

import java.util.ArrayList;
import java.util.List;

public class ContestManager {

    private List<Contest> contests = new ArrayList<>();

    public void addContest(Contest constest){
        this.contests.add(constest);
    }

    public int contestsSize(){
        return this.contests.size();
    }

}
