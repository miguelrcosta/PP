package league;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Standing implements IStanding {

    private final ITeam team;

    private int wins = 0;
    private int draws = 0;
    private int losses = 0;
    private int goalsScored = 0;
    private int goalsConceded = 0;
    private int points = 0;

    public Standing(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Team can't be null.");
        }
        this.team = team;
    }

    @Override
    public void addWin(int pointsPerWin) {
        if (pointsPerWin < 0) {
            throw new IllegalArgumentException("Negative points doesn't exist");
        }
        wins++;
        points += pointsPerWin;
    }

    @Override
    public void addDraw(int pointsPerDraw) {
        if (pointsPerDraw < 0) {
            throw new IllegalArgumentException("Negative points doesn't exist");
        }
        draws++;
        points += pointsPerDraw;
    }

    @Override
    public void addLoss(int pointsPerLoss) {
        if (pointsPerLoss < 0) {
            throw new IllegalArgumentException("Negative points doesn't exist");
        }
        losses++;
        points += pointsPerLoss;
    }

    @Override
    public void addPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Negative points doesn't exist");
        }
        this.points += points;
    }

    @Override
    public int getWins() {
        return this.wins;
    }

    @Override
    public int getDraws() {
        return this.draws;
    }

    @Override
    public int getLosses() {
        return this.losses;
    }

    @Override
    public int getGoalScored() {
        return this.goalsScored;
    }

    @Override
    public int getGoalsConceded() {
        return this.goalsConceded;
    }

    @Override
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public int getTotalMatches() {
        return wins + draws + losses;
    }

    @Override
    public ITeam getTeam() {
        if (team == null) {
            throw new IllegalStateException("Team doesn't exist.");
        }
        return team;
    }

    public void setGoals(int scored, int conceded) {
        if (scored < 0 || conceded < 0) {
            throw new IllegalArgumentException("Goals can't be negative");
        }
        this.goalsScored += scored;
        this.goalsConceded += conceded;
    }
}
