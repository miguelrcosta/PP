package org.example;

import org.example.team.Club;
import org.example.team.Team;
import org.example.player.Player;
import org.example.player.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // Criar um Club
            Club benfica = new Club("SLB", "Portugal", 1904,
                    "https://media.api-sports.io/football/teams/211.png",
                    "Sport Lisboa e Benfica", "Estádio da Luz");

            // Adicionar jogador ao Club
            IPlayerPosition gk = new PlayerPosition("Goalkeeper");
            Player andreGomes = new Player(
                    19, LocalDate.of(2004, 10, 20),
                    1.88f, "André Gomes", "Portuguese",
                    75, 70, "https://www.slbenfica.pt/pt-pt/players/andre-gomes",
                    gk, PreferredFoot.Right, 60, 50, 80, 80.0f
            );
            benfica.addPlayer(andreGomes);

            // Testar exportToJson do Club
            benfica.exportToJson();
            System.out.println("Club JSON exportado com sucesso!");

            // Criar um Team associado ao Club
            Team team = new Team(benfica, new org.example.team.Formation("4-4-2"));
            team.addPlayer(andreGomes);

            // Testar exportToJson do Team
            team.exportToJson();
            System.out.println("Team JSON exportado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
