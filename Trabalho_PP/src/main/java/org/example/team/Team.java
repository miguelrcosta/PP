package org.example.team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.IOException;
import java.util.Arrays;

public class Team implements ITeam {
    private static final int MAX_PLAYERS = 11;

    private IClub club;
    private IFormation formation;
    private IPlayer[] players = new IPlayer[MAX_PLAYERS];
    private int playerCount = 0;

    public Team(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("Club cannot be null.");
        }
        this.club = club;
    }

    @Override
    public IClub getClub() {
        return this.club;
    }

    @Override
    public IFormation getFormation() {
        if (formation == null) {
            throw new IllegalStateException("Formation is not set.");
        }
        return this.formation;
    }

    @Override
    public IPlayer[] getPlayers() {
        return Arrays.copyOf(players, playerCount);
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }

        if (formation == null) {
            throw new IllegalStateException("Formation is not set.");
        }

        if (playerCount >= MAX_PLAYERS) {
            throw new IllegalStateException("Team is full.");
        }

        for (int i = 0; i < playerCount; i++) {
            if (players[i].equals(player)) {
                throw new IllegalStateException("Player is already in the team.");
            }
        }

        boolean playerInClub = Arrays.stream(club.getPlayers()).anyMatch(p -> p.equals(player));
        if (!playerInClub) {
            throw new IllegalStateException("Player does not belong to the club.");
        }

        players[playerCount++] = player;
    }

    @Override
    public int getPositionCount(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }

        int count = 0;
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getPosition().equals(position)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        if (formation == null) {
            return false;
        }
        return formation.getClass().contains(position);
    }

    @Override
    public int getTeamStrength() {
        if (playerCount == 0) {
            return 0;
        }

        int totalStrength = 0;
        for (int i = 0; i < playerCount; i++) {
            totalStrength += players[i].getAttributes().getOverall(); // assuming getOverall() returns int (0–100)
        }

        return totalStrength / playerCount;
    }

    @Override
    public void setFormation(IFormation formation) {
        if (formation == null) {
            throw new IllegalArgumentException("Formation cannot be null.");
        }
        this.formation = formation;
    }

    @Override
    public void exportToJson() throws IOException {
        // Implement JSON export logic based on your environment or leave empty if handled elsewhere
    }
}
