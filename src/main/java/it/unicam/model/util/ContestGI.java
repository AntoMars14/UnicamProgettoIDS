package it.unicam.model.util;

public class ContestGI {

    private final int contestId;
    private final String name;
    private final String objective;
    private final boolean isClosed;

    public ContestGI(int contestId, String name, String objective, boolean isClosed) {
        this.contestId = contestId;
        this.name = name;
        this.objective = objective;
        this.isClosed = isClosed;
    }

    public int getContestId() {
        return contestId;
    }

    public String getName() {
        return name;
    }

    public String getObjective() {
        return objective;
    }

    public boolean isClosed() {
        return isClosed;
    }


    @Override
    public String toString() {
        return "Contest " +
                "Id = " + contestId +
                " - name=" + name +
                " - objective = " + objective +
                " - isClosed = " + isClosed;
    }
}
