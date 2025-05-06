package org.example.team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;

public class Club implements IClub {
    private static final int MAX_PLAYERS = 33;
    private String code;
    private String country;
    private int foundedYear;
    private String logo;
    private String name;
    private IPlayer[] players;
    private int playerCount;
    private String stadiumName;

    public Club(String code, String country, int foundedYear, String logo, String name, String stadiumName) {
        this.code = code;
        this.country = country;
        this.foundedYear = foundedYear;
        this.logo = logo;
        this.name = name;
        this.players = new IPlayer[MAX_PLAYERS];
        this.playerCount = 0;
        this.stadiumName = stadiumName;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        if (playerCount >= MAX_PLAYERS) {
            throw new IllegalArgumentException("Maximum number of players reached.");
        }
        if (isPlayer(player)) {
            throw new IllegalArgumentException("Player already exists in the club.");
        }
        players[playerCount++] = player;
    }

    @Override
    public void removePlayer(IPlayer player) {
        int index = this.findPlayerIndex(player);

        if (index == -1) {
            throw new IllegalArgumentException("Player is not in the club.");
        }

        if (player == null) {
            throw new IllegalArgumentException("Player can't be null.");
        }

        for (int i = index; i < this.playerCount - 1; i++) {
            this.players[i] = this.players[i + 1];
        }

        this.players[--this.playerCount] = null;
    }

    private int findPlayerIndex(IPlayer player) {
        for (int i = 0; i < this.playerCount; i++) {
            if (this.players[i].equals(player)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isPlayer(IPlayer player) {
        // Check if the player is already in the club
    }

    @Override
    public boolean isValid() {
        // Check if the club has a valid name, country, and founded year
    }

    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] player = new IPlayer[playerCount];
        for (int i = 0; i < playerCount; i++) {
            player[i] = players[i];
        }
        return player;
    }

    @Override
    public int getPlayerCount() {
        return this.playerCount;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public int getFoundedYear() {
        return this.foundedYear;
    }

    @Override
    public String getStadiumName() {
        return this.stadiumName;
    }

    @Override
    public String getLogo() {
        return this.logo;
    }

    @Override
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
        //
    }

    @Override
    public void exportToJson() throws IOException {

    }
}
