package org.example;

import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import org.example.league.Season;
import org.example.match.Match;
import org.example.team.Club;
import org.example.player.Player;
import org.example.player.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import org.example.team.Formation;
import org.example.team.Team;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Create a season
        Season season = new Season(2025);

        // Create 4 clubs
        IClub club1 = new Club("CLB1", "Portugal", 1900, "logo1", "FC Porto", "DragÃ£o");
        IClub club2 = new Club("CLB2", "Portugal", 1904, "logo2", "SL Benfica", "Luz");
        IClub club3 = new Club("CLB3", "Portugal", 1906, "logo3", "Sporting CP", "Alvalade");
        IClub club4 = new Club("CLB4", "Portugal", 1910, "logo4", "Braga", "Pedreira");

        // Add 16 players to each club (1 GK and 15 others)
        for (IClub club : new IClub[]{club1, club2, club3, club4}) {
            club.addPlayer(createPlayer("GK", club.getName() + " GK"));
            for (int i = 0; i < 15; i++) {
                club.addPlayer(createPlayer("ST", club.getName() + " ST" + i));
            }
            season.addClub(club);
        }

        // Generate schedule (adds matches internally)
        season.generateSchedule();

        // Set dummy simulator
        season.setMatchSimulator(new MatchSimulatorStrategy() {
            @Override
            public void simulate(IMatch match) {
                System.out.println("Simulating: " + match.getHomeClub().getName() + " vs " + match.getAwayClub().getName());
                match.setPlayed();
            }
        });

        // Simulate 1 round
        season.simulateRound();

        // Display results
        IMatch[] matches = season.getMatches();
        System.out.println("\nResults:");
        for (IMatch match : matches) {
            if (match.isPlayed()) {
                System.out.println(season.displayMatchResult(match));
            }
        }
    }

    private static Player createPlayer(String positionDesc, String name) {
        return new Player(
                25,
                LocalDate.of(2000, 1, 1),
                1.80f,
                name,
                "Portugal",
                10,
                70,
                "photo.png",
                new PlayerPosition(positionDesc),
                PreferredFoot.Right,
                75,
                80,
                85,
                75.0f
        );
    }
}