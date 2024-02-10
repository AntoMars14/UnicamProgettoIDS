package it.unicam.model;

public class Contest {

    private int id;
    private String name;
    private String objective;
    private boolean onInvite;
    private boolean isClosed;

    public Contest(String name, String objective) {
        this.name = name;
        this.objective = objective;
        this.isClosed = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public boolean isOnInvite() {
        return onInvite;
    }

    public void setOnInvite(boolean onInvite) {
        this.onInvite = onInvite;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    @Override
    public String toString() {
        return "Contest " +
                "id = " + id +
                " - name = " + name +
                "\nobjective = " + objective +
                "\nonInvite = " + onInvite +
                " - isClosed = " + isClosed;
    }
}
