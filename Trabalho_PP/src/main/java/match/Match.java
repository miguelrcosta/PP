package match;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.IOException;

public class Match implements IMatch {

    private static final int MAX_EVENTS = 500;

    private IClub homeClub;
    private IClub awayClub;
    private ITeam homeTeam;
    private ITeam awayTeam;
    private int round;
    private boolean played;
    private IEvent[] events = new IEvent[MAX_EVENTS];
    private int eventCount = 0;

    @Override
    public IClub getHomeClub() {
        if (homeClub == null) throw new IllegalStateException("Home club is not initialized.");
        return homeClub;
    }

    @Override
    public IClub getAwayClub() {
        if (awayClub == null) throw new IllegalStateException("Away club is not initialized.");
        return awayClub;
    }

    @Override
    public boolean isPlayed() {
        return played;
    }

    @Override
    public ITeam getHomeTeam() {
        if (homeTeam == null) throw new IllegalStateException("Home team is not initialized.");
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        if (awayTeam == null) throw new IllegalStateException("Away team is not initialized.");
        return awayTeam;
    }

    @Override
    public void setPlayed() {
        this.played = true;
    }

    @Override
    public int getTotalByEvent(Class eventClass, IClub team) {
        if (eventClass == null || team == null) throw new IllegalArgumentException("Event class and team cannot be null.");

        int count = 0;
        for (int i = 0; i < eventCount; i++) {
            if (events[i] != null && events[i].getClass().equals(eventClass)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean isValid() {
        return homeTeam != null && awayTeam != null &&
                homeClub != null && awayClub != null &&
                !homeTeam.equals(awayTeam);
    }

    @Override
    public ITeam getWinner() {
        int homeGoals = getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, homeClub);
        int awayGoals = getTotalByEvent(com.ppstudios.footballmanager.api.contracts.event.IGoalEvent.class, awayClub);

        if (homeGoals > awayGoals) {
            return homeTeam;
        } else if (awayGoals > homeGoals) {
            return awayTeam;
        } else {
            return null; // Caso de empate
        }
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public void setTeam(ITeam team) {
        if (team == null) throw new IllegalArgumentException("Team cannot be null.");
        if (played) throw new IllegalStateException("Cannot set team after match is played.");
        if (homeTeam == null) {
            homeTeam = team;
            homeClub = team.getClub();
        } else if (awayTeam == null) {
            awayTeam = team;
            awayClub = team.getClub();
        } else {
            throw new IllegalStateException("Both teams are already set.");
        }
    }

    @Override
    public void exportToJson() throws IOException {
    }

    @Override
    public void addEvent(IEvent event) {
        if (event == null) throw new IllegalArgumentException("Event cannot be null.");
        if (eventCount >= MAX_EVENTS) {
            throw new IllegalStateException("Maximum number of events reached.");
        }
        events[eventCount++] = event;
    }

    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            result[i] = events[i];
        }
        return result;
    }

    @Override
    public int getEventCount() {
        return eventCount;
    }
}
