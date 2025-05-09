package org.example.league;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import org.example.match.Match;

import java.io.IOException;

public class Season implements ISeason {

    private static final int MAX_TEAMS = 20;
    private static final int MAX_MATCHES = MAX_TEAMS * (MAX_TEAMS - 1); // Each team plays all others once
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
        if (club == null || clubCount >= MAX_TEAMS) return false;

        for (int i = 0; i < clubCount; i++) {
            if (clubs[i].equals(club)) {
                return false;
            }
        }

        clubs[clubCount++] = club;
        return true;
    }

    @Override
    public boolean removeClub(IClub club) {
        if (club == null) return false;

        for (int i = 0; i < clubCount; i++) {
            if (clubs[i].equals(club)) {
                for (int j = i; j < clubCount - 1; j++) {
                    clubs[j] = clubs[j + 1];
                }
                clubs[--clubCount] = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public void generateSchedule() {
        matchCount = 0;
        for (int i = 0; i < clubCount; i++) {
            for (int j = 0; j < clubCount; j++) {
                if (i != j) {
                    IMatch match = new Match();
                    match.setTeam(new org.example.team.Team(clubs[i], new org.example.team.Formation("4-4-2")));
                    match.setTeam(new org.example.team.Team(clubs[j], new org.example.team.Formation("4-4-2")));
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
        if (simulator == null) {
            throw new IllegalStateException("Match simulator strategy is not set.");
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
        return new IStanding[0];
    }

    @Override
    public ISchedule getSchedule() {
        return null;
    }

    @Override
    public int getPointsPerWin() {
        return POINTS_PER_WIN;
    }

    @Override
    public int getPointsPerDraw() {
        return POINTS_PER_DRAW;
    }

    @Override
    public int getPointsPerLoss() {
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
        // Optional implementation
    }
}
