package league;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;

import java.io.IOException;

public class League implements ILeague {

    private final String name;
    private final ISeason[] seasons;
    private int seasonCount;

    public League(String name, int maxSeasons) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("League name can't be null or empty.");
        }
        if (maxSeasons <= 0) {
            throw new IllegalArgumentException("Number of seasons must be greater than 0.");
        }

        this.name = name;
        this.seasons = new ISeason[maxSeasons];
        this.seasonCount = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ISeason[] getSeasons() {
        ISeason[] season = new ISeason[seasonCount];
        for (int i = 0; i < seasonCount; i++) {
            season[i] = seasons[i];
        }
        return season;
    }

    @Override
    public ISeason getSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                return seasons[i];
            }
        }
        throw new IllegalArgumentException("Season not found for year: " + year);
    }

    @Override
    public ISeason removeSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                ISeason remove = seasons[i];
                for (int j = i; j < seasonCount - 1; j++) {
                    seasons[j] = seasons[j + 1];
                }
                seasons[--seasonCount] = null;
                return remove;
            }
        }
        throw new IllegalArgumentException("Season not found");
    }

    @Override
    public boolean createSeason(ISeason season) {
        if (season == null) {
            throw new IllegalArgumentException("Season can't be null.");
        }

        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == season.getYear()) {
                throw new IllegalArgumentException("Season already exists for the year you wrote");
            }
        }

        if (seasonCount >= seasons.length) {
            return false;
        }

        seasons[seasonCount++] = season;
        return true;
    }

    @Override
    public void exportToJson() throws IOException {
        // Implementar depois
    }
}
