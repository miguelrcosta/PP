package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

public class EventManager implements IEventManager {

    private static final int MAX_EVENTS = 500;

    private final IEvent[] events = new IEvent[MAX_EVENTS];
    private int eventCount = 0;

    @Override
    public void addEvent(IEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

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
        return this.eventCount;
    }
}
