package it.unicam.model.util;

public class ContestGI {

    private final Long contestId;
    private final String name;
    private final String objective;
    private final boolean isClosed;
    private boolean onInvite;

    public ContestGI(Long contestId, String name, String objective, boolean isClosed) {
        this.contestId = contestId;
        this.name = name;
        this.objective = objective;
        this.isClosed = isClosed;
    }

    public boolean isOnInvite() {
        return onInvite;
    }

    public Long getContestId() {
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
