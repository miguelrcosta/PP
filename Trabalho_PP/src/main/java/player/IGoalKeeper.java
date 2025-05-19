package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public interface IGoalKeeper extends IPlayer {

    int getReflexes();

    int getDiving();

    int getHandling();

}
