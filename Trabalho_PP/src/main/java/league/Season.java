package league;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import match.Match;

import java.io.IOException;

public class Season implements ISeason {

    private static final int MAX_TEAMS = 18;
    private static final int MAX_MATCHES = MAX_TEAMS * (MAX_TEAMS - 1);
    private static final int POINTS_PER_WIN = 3;
    private static final int POINTS_PER_DRAW = 1;
    private static final int POINTS_PER_LOSS = 0;

    private int year;
    private IClub[] clubs = new IClub[MAX_TEAMS];
    private int clubCount = 0;

    private IMatch[] matches = new IMatch[MAX_MATCHES];
    private int matchCount = 0;

    private int currentRound = 0;
    private MatchSimulatorStrategy simulator;

    public Season(int year) {
        this.year = year;
    }

    @Override
    public int getYear() {
        return this.year;
    }


    @Override
    public boolean addClub(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("Club cannot be null.");
        }

        for (int i = 0; i < clubCount; i++) {
            if (clubs[i].equals(club)) {
                throw new IllegalArgumentException("Club is already in the league.");
            }
        }

        if (clubCount >= MAX_TEAMS) {
            throw new IllegalStateException("The league is full.");
        }

        clubs[clubCount++] = club;


        return true;
    }

    @Override
    public boolean removeClub(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("Club cannot be null.");
        }

        for (int i = 0; i < clubCount; i++) {
            if (clubs[i].equals(club)) {
                for (int j = i; j < clubCount - 1; j++) {
                    clubs[j] = clubs[j + 1];
                }
                clubs[--clubCount] = null;

                return true;
            }
        }

        throw new IllegalStateException("Club is not in the league.");
    }


    @Override
    public void generateSchedule() {
        if (clubCount == 0) {
            throw new IllegalStateException("Cannot generate schedule: the league is empty.");
        }

        for (int i = 0; i < matchCount; i++) {
            if (matches[i] != null && matches[i].isPlayed()) {
                throw new IllegalStateException("Cannot generate schedule: a match has already been played.");
            }
        }

        matchCount = 0;

        for (int i = 0; i < clubCount; i++) {
            for (int j = 0; j < clubCount; j++) {
                if (i != j) {
                    IMatch match = new Match();
                    match.setTeam(new team.Team(clubs[i], new team.Formation("4-4-2")));
                    match.setTeam(new team.Team(clubs[j], new team.Formation("4-4-2")));
                    matches[matchCount++] = match;
                }
            }
        }
    }

    @Override
    public IMatch[] getMatches() {
        IMatch[] result = new IMatch[matchCount];
        for (int i = 0; i < matchCount; i++) {
            result[i] = matches[i];
        }
        return result;
    }

    @Override
    public IMatch[] getMatches(int round) {
        int count = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matches[i].getRound() == round) count++;
        }

        IMatch[] result = new IMatch[count];
        int index = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matches[i].getRound() == round) {
                result[index++] = matches[i];
            }
        }

        return result;
    }

    @Override
    public void simulateRound() {
        if (clubCount == 0) {
            throw new IllegalStateException("League is empty.");
        }

        if (matchCount == 0) {
            throw new IllegalStateException("No matches scheduled.");
        }

        if (simulator == null) {
            throw new IllegalStateException("Match simulator strategy isn't set.");
        }

        for (int i = 0; i < matchCount; i++) {
            IMatch match = matches[i];
            if (!match.isPlayed()) {
                simulator.simulate(match);
                match.setPlayed();
            }
        }

        currentRound++;
    }


    @Override
    public void simulateSeason() {
        if (clubCount == 0) {
            throw new IllegalStateException("League is empty.");
        }

        if (matchCount == 0) {
            throw new IllegalStateException("League is not scheduled");
        }

        while (!isSeasonComplete()) {
            simulateRound();
        }
    }


    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public boolean isSeasonComplete() {
        for (int i = 0; i < matchCount; i++) {
            if (!matches[i].isPlayed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void resetSeason() {
        for (int i = 0; i < matchCount; i++) {
            matches[i] = null;
        }
        matchCount = 0;
        currentRound = 0;
    }

    @Override
    public String displayMatchResult(IMatch match) {
        if (match == null) return "Match is null.";
        return match.getHomeClub().getName() + " vs " + match.getAwayClub().getName();
    }

    @Override
    public void setMatchSimulator(MatchSimulatorStrategy matchSimulatorStrategy) {
        this.simulator = matchSimulatorStrategy;
    }

    @Override
    public IStanding[] getLeagueStandings() {
        Standing[] standings = new Standing[clubCount];
        int count = 0;

        for (int i = 0; i < matchCount; i++) {
            IMatch match = matches[i];
            if (!match.isPlayed()) continue;

            ITeam home = match.getHomeTeam();
            ITeam away = match.getAwayTeam();

            Standing homeStanding = null;
            Standing awayStanding = null;

            // Verifica se já existe uma Standing para as equipas
            for (int j = 0; j < count; j++) {
                if (standings[j].getTeam().equals(home)) homeStanding = standings[j];
                if (standings[j].getTeam().equals(away)) awayStanding = standings[j];
            }

            // Se ainda não existir, criar nova
            if (homeStanding == null) {
                homeStanding = new Standing(home);
                standings[count++] = homeStanding;
            }
            if (awayStanding == null) {
                awayStanding = new Standing(away);
                standings[count++] = awayStanding;
            }

            int homeGoals = match.getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, match.getHomeClub());
            int awayGoals = match.getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, match.getAwayClub());

            homeStanding.setGoals(homeGoals, awayGoals);
            awayStanding.setGoals(awayGoals, homeGoals);

            if (homeGoals > awayGoals) {
                homeStanding.addWin(getPointsPerWin());
                awayStanding.addLoss(getPointsPerLoss());
            } else if (awayGoals > homeGoals) {
                awayStanding.addWin(getPointsPerWin());
                homeStanding.addLoss(getPointsPerLoss());
            } else {
                homeStanding.addDraw(getPointsPerDraw());
                awayStanding.addDraw(getPointsPerDraw());
            }
        }

        IStanding[] result = new IStanding[count];
        for (int i = 0; i < count; i++) {
            result[i] = standings[i];
        }
        return result;
    }


    @Override
    public ISchedule getSchedule() {
        return null;
    }

    @Override
    public int getPointsPerWin() {
        if (simulator == null) {
            throw new IllegalStateException("Match simulator isn't initialized.");
        }
        return POINTS_PER_WIN;
    }

    @Override
    public int getPointsPerDraw() {
        if (simulator == null) {
            throw new IllegalStateException("Match simulator isn't initialized.");
        }
        return POINTS_PER_DRAW;
    }

    @Override
    public int getPointsPerLoss() {
        if (simulator == null) {
            throw new IllegalStateException("Match simulator isn't initialized.");
        }
        return POINTS_PER_LOSS;
    }


    @Override
    public String getName() {
        return "Season " + year;
    }

    @Override
    public int getMaxTeams() {
        return MAX_TEAMS;
    }

    @Override
    public int getMaxRounds() {
        return (MAX_TEAMS - 1) * 2;
    }

    @Override
    public int getCurrentMatches() {
        int played = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matches[i].isPlayed()) played++;
        }
        return played;
    }

    @Override
    public int getNumberOfCurrentTeams() {
        return clubCount;
    }

    @Override
    public IClub[] getCurrentClubs() {
        IClub[] result = new IClub[clubCount];
        for (int i = 0; i < clubCount; i++) {
            result[i] = clubs[i];
        }
        return result;
    }

    @Override
    public void exportToJson() throws IOException {
        // Implementar mais tarde
    }
}
