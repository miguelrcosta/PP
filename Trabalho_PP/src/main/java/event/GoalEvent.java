package event;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

public class GoalEvent implements IGoalEvent {

    private final IPlayer player;
    private final int minute;

    public GoalEvent(IPlayer player, int minute) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        if (minute < 0 || minute > 90) {
            throw new IllegalArgumentException("Minute must be between 0 and 120.");
        }

        this.player = player;
        this.minute = minute;
    }

    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    @Override
    public String getDescription() {
        return "Goal by " + player.getName() + " at " + minute + "'";
    }

    @Override
    public int getMinute() {
        return this.minute;
    }

    @Override
    public void exportToJson() throws IOException {
        // Implementar mais tarde
    }
}
