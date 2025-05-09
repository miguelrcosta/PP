package org.example.event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;

import java.io.FileOutputStream;
import java.io.IOException;

public class Event implements IEvent {

    private final String description;
    private final int minute;

    public Event(String description, int minute) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        if (minute < 0 || minute > 90) {
            throw new IllegalArgumentException("Minute must be between 0 and 120.");
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
        String json = "{\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"minute\": " + minute + "\n" +
                "}";

        String fileName = "event_" + minute + "_" + description.replaceAll("\\s+", "_") + ".json";
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(json.getBytes());
        fos.close();
    }
}

