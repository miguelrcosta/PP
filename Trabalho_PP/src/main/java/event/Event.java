package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;

import java.io.IOException;

public class Event implements IEvent {

    private final String description;
    private final int minute;

    public Event(String description, int minute) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description can't be null or empty.");
        }
        if (minute < 0 || minute > 90) {
            throw new IllegalArgumentException("Minute must be between 0' and 90'.");
        }
        this.description = description;
        this.minute = minute;
    }

    @Override
    public String getDescription() {
        return this.description;
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

