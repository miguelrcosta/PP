package org.example.league;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.IOException;

public class Schedule implements ISchedule {

    private static final int MAX_MATCHES = 380;
    private static final int MAX_ROUNDS = 38;

    private IMatch[] allMatches = new IMatch[MAX_MATCHES];
    private int matchCount = 0;

    private int[] matchIndexesByRound = new int[MAX_MATCHES]; // parallel array for round mapping
    private int currentRound = 0;

    public boolean addMatchToRound(IMatch match, int round) {
        if (match == null || round < 0 || round >= MAX_ROUNDS || matchCount >= MAX_MATCHES) {
            return false;
        }

        allMatches[matchCount] = match;
        matchIndexesByRound[matchCount] = round;
        matchCount++;
        return true;
    }

    @Override
    public IMatch[] getMatchesForRound(int round) {
        int count = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matchIndexesByRound[i] == round) {
                count++;
            }
        }

        IMatch[] result = new IMatch[count];
        int index = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matchIndexesByRound[i] == round) {
                result[index++] = allMatches[i];
            }
        }

        return result;
    }

    @Override
    public IMatch[] getMatchesForTeam(ITeam team) {
        if (team == null) return new IMatch[0];

        int count = 0;
        for (int i = 0; i < matchCount; i++) {
            IMatch match = allMatches[i];
            if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                count++;
            }
        }

        IMatch[] result = new IMatch[count];
        int index = 0;
        for (int i = 0; i < matchCount; i++) {
            IMatch match = allMatches[i];
            if (match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team)) {
                result[index++] = match;
            }
        }

        return result;
    }

    @Override
    public int getNumberOfRounds() {
        int maxRound = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matchIndexesByRound[i] > maxRound) {
                maxRound = matchIndexesByRound[i];
            }
        }
        return maxRound + 1;
    }

    @Override
    public IMatch[] getAllMatches() {
        IMatch[] result = new IMatch[matchCount];
        for (int i = 0; i < matchCount; i++) {
            result[i] = allMatches[i];
        }
        return result;
    }

    @Override
    public void setTeam(ITeam team, int matchIndex) {
        if (team == null || matchIndex < 0 || matchIndex >= matchCount) {
            throw new IllegalArgumentException("Invalid team or match index.");
        }

        IMatch match = allMatches[matchIndex];
        match.setTeam(team);
    }

    @Override
    public void exportToJson() throws IOException {
        // Optional: implement if needed
    }
}
