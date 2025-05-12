package team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

public class PlayerSelector implements IPlayerSelector {

    @Override
    public IPlayer selectPlayer(IClub club, IPlayerPosition position) {
        if (club == null || position == null) {
            throw new IllegalArgumentException("Club and position can't be null.");
        }

        IPlayer[] players = club.getPlayers();
        if (players == null || players.length == 0) {
            throw new IllegalStateException("The team is empty.");
        }

        for (IPlayer player : players) {
            if (player.getPosition().equals(position)) {
                return player;
            }
        }

        throw new IllegalStateException("No player found for the specified position.");
    }
}
