package it.unicam.model.util;

public class ContestGI {

    private Long contestId;
    private String name;
    private String objective;
    private boolean onInvite;
    private boolean isClosed;

    public ContestGI(Long contestId, String name, String objective, boolean onInvite, boolean isClosed) {
        this.contestId = contestId;
        this.name = name;
        this.objective = objective;
        this.isClosed = isClosed;
        this.onInvite = onInvite;
    }

    public ContestGI() {
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
    public void isOnInvite(boolean onInvite) {
        this.onInvite = onInvite;
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
