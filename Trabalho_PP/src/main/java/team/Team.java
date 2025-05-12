package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import player.PlayerPosition;

import java.io.IOException;

public class Team implements ITeam {

    private static final int MAX_PLAYERS = 11;

    private IClub club;
    private IFormation formation;
    private IPlayer[] players;
    private int playerCount;

    public Team(IClub club, IFormation formation) {
        if (club == null) {
            throw new IllegalArgumentException("Club cannot be null.");
        }
        if (formation == null) {
            throw new IllegalArgumentException("Formation cannot be null.");
        }
        this.club = club;
        this.formation = formation;
        this.players = new IPlayer[MAX_PLAYERS];
        this.playerCount = 0;
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
    public void setFormation(IFormation formation) {
        if (formation == null) {
            throw new IllegalArgumentException("Formation cannot be null.");
        }
        this.formation = formation;
    }

    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] copy = new IPlayer[playerCount];
        for (int i = 0; i < playerCount; i++) {
            copy[i] = players[i];
        }
        return copy;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }

        if (playerCount >= MAX_PLAYERS) {
            throw new IllegalStateException("Team is full.");
        }
        for (int i = 0; i < playerCount; i++) {
            if (players[i].equals(player)) {
                throw new IllegalStateException("Player is already in the team.");
            }
        }
        if (!club.isPlayer(player)) {
            throw new IllegalStateException("Player does not belong to the team.");
        }
        if (formation == null) {
            throw new IllegalStateException("Formation is not set.");
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
        if (formation == null || position == null) {
            return false;
        }

        IPlayerPosition[] allowedPositions = getAllowedPositionsByDescription(formation.getDisplayName());
        for (IPlayerPosition p : allowedPositions) {
            if (p.getDescription().equals(position.getDescription())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getTeamStrength() {
        if (playerCount == 0) {
            return 0;
        }

        int totalStrength = 0;

        for (int i = 0; i < playerCount; i++) {
            totalStrength += players[i].getShooting();
            totalStrength += players[i].getPassing();
            totalStrength += players[i].getSpeed();
            totalStrength += players[i].getStamina();
        }

        return totalStrength / (playerCount * 4);
    }

    @Override
    public void exportToJson() throws IOException {
        // Fazer mais tarde
    }

    private IPlayerPosition[] getAllowedPositionsByDescription(String description) {
        if (description.equals("4-4-2")) {
            return new IPlayerPosition[]{
                    new PlayerPosition("GR"),
                    new PlayerPosition("DD"),
                    new PlayerPosition("DC"),
                    new PlayerPosition("DC"),
                    new PlayerPosition("DE"),
                    new PlayerPosition("MD"),
                    new PlayerPosition("MC"),
                    new PlayerPosition("MC"),
                    new PlayerPosition("ME"),
                    new PlayerPosition("PL"),
                    new PlayerPosition("PL")
            };
        } else if (description.equals("3-5-2")) {
            return new IPlayerPosition[]{
                    new PlayerPosition("GR"),
                    new PlayerPosition("DC"),
                    new PlayerPosition("DC"),
                    new PlayerPosition("DC"),
                    new PlayerPosition("MD"),
                    new PlayerPosition("MC"),
                    new PlayerPosition("MC"),
                    new PlayerPosition("ME"),
                    new PlayerPosition("MO"),
                    new PlayerPosition("PL"),
                    new PlayerPosition("PL")
            };
        }
        return new IPlayerPosition[0];
    }
}
