package it.unicam.model.util.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContestGI {

    private Long contestId;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String objective;
    @NotNull
    private boolean onInvite;
    @NotNull
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
