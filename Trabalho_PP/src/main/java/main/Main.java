package main;

import event.*;
import league.Season;
import match.Match;
import player.Player;
import player.PlayerPosition;
import team.*;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // Criar posições
            PlayerPosition goleiro = new PlayerPosition("GK");
            PlayerPosition atacante = new PlayerPosition("PL");

            // Criar jogadores
            Player p1 = new Player(25, LocalDate.of(2000, 1, 1), 1.80f, "João", "Portugal", 10, 80, "joao.jpg", goleiro, PreferredFoot.Right, 50, 60, 70, 75f);
            Player p2 = new Player(27, LocalDate.of(1998, 5, 12), 1.75f, "Miguel", "Brasil", 9, 85, "miguel.jpg", atacante, PreferredFoot.Left, 90, 85, 80, 70f);

            // Criar clubes
            Club club1 = new Club("FCP", "Portugal", 1893, "fcp_logo.png", "FC Porto", "Dragão");
            Club club2 = new Club("SLB", "Portugal", 1904, "slb_logo.png", "SL Benfica", "Luz");

            // Adicionar jogadores ao clube
            for (int i = 0; i < 16; i++) {
                club1.addPlayer(new Player(20 + i, LocalDate.of(2000, 1, 1), 1.80f, "PlayerFCP" + i, "Portugal", i, 70, "photo.png", goleiro, PreferredFoot.Right, 70, 70, 70, 75f));
                club2.addPlayer(new Player(20 + i, LocalDate.of(2000, 1, 1), 1.80f, "PlayerSLB" + i, "Portugal", i, 70, "photo.png", goleiro, PreferredFoot.Right, 70, 70, 70, 75f));
            }

            // Validar clubes
            System.out.println("FC Porto válido? " + club1.isValid());
            System.out.println("SL Benfica válido? " + club2.isValid());

            // Criar formações
            Formation f442 = new Formation("4-4-2");

            // Criar times
            Team team1 = new Team(club1, f442);
            Team team2 = new Team(club2, f442);

            // Adicionar jogadores ao time
            for (int i = 0; i < 11; i++) {
                team1.addPlayer(club1.getPlayers()[i]);
                team2.addPlayer(club2.getPlayers()[i]);
            }

            // Criar partida
            Match match = new Match();
            match.setTeam(team1);
            match.setTeam(team2);

            // Adicionar eventos
            GoalEvent goal1 = new GoalEvent(p1, 10);
            GoalEvent goal2 = new GoalEvent(p2, 55);
            match.addEvent(goal1);
            match.addEvent(goal2);

            // Marcar partida como jogada
            match.setPlayed();

            // Exibir vencedor
            System.out.println("Vencedor: " + (match.getWinner() != null ? match.getWinner().getClub().getName() : "Empate"));

            // Criar campeonato
            Season season = new Season(2025);
            season.addClub(club1);
            season.addClub(club2);
            season.generateSchedule();

            // Simular rodada
            season.setMatchSimulator(m -> {
                m.addEvent(new GoalEvent(p1, 15));
                m.addEvent(new GoalEvent(p2, 30));
                m.setPlayed();
            });
            season.simulateRound();

            // Exibir partidas
            for (var m : season.getMatches()) {
                System.out.println(season.displayMatchResult(m));
                for (var e : m.getEvents()) {
                    System.out.println("  Evento: " + e.getDescription());
                }
            }

            // Exportar para JSON (alguns são opcionais no seu código)
            club1.exportToJson();
            team1.exportToJson();
            goal1.exportToJson();
            match.exportToJson();
            season.exportToJson();

            System.out.println("Exportação para JSON concluída.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
