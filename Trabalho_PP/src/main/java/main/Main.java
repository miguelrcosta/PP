package main;

import player.Player;
import player.PlayerPosition;
import team.Club;
import team.Team;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Club club = new Club("FCF", "Portugal", 1952, "logo.png", "FC Felgueiras", "Estádio Dr. Machado de Matos");

        // Criar 16 jogadores
        IPlayer[] jogadores = new IPlayer[]{
                new Player(30, LocalDate.of(1994, 3, 1), 1.90f, "Tiago Lopes", "Portugal", 1, 60, "tiago.jpg", new PlayerPosition("GR"), PreferredFoot.Both, 40, 50, 70, 82.0f),
                new Player(26, LocalDate.of(1998, 7, 2), 1.80f, "Nuno Ferreira", "Portugal", 2, 65, "nuno.jpg", new PlayerPosition("DD"), PreferredFoot.Both, 50, 60, 75, 76.0f),
                new Player(28, LocalDate.of(1996, 2, 15), 1.85f, "Pedro Oliveira", "Portugal", 3, 75, "pedro.jpg", new PlayerPosition("DC"), PreferredFoot.Both, 60, 70, 80, 80.0f),
                new Player(29, LocalDate.of(1995, 6, 10), 1.86f, "João Pires", "Brasil", 4, 70, "joao.jpg", new PlayerPosition("DC"), PreferredFoot.Both, 63, 72, 78, 79.5f),
                new Player(27, LocalDate.of(1997, 5, 9), 1.77f, "Miguel Reis", "Portugal", 5, 68, "miguel.jpg", new PlayerPosition("DE"), PreferredFoot.Both, 58, 66, 79, 74.0f),
                new Player(25, LocalDate.of(1999, 3, 12), 1.78f, "Marco Teixeira", "Brasil", 6, 88, "marco.jpg", new PlayerPosition("MD"), PreferredFoot.Both, 72, 82, 85, 73.5f),
                new Player(24, LocalDate.of(2000, 1, 5), 1.81f, "Luís Gomes", "Angola", 7, 82, "luis.jpg", new PlayerPosition("MC"), PreferredFoot.Both, 70, 85, 80, 78.0f),
                new Player(23, LocalDate.of(2001, 9, 14), 1.80f, "Carlos Silva", "Portugal", 8, 85, "carlos.jpg", new PlayerPosition("MC"), PreferredFoot.Both, 75, 84, 82, 75.0f),
                new Player(22, LocalDate.of(2002, 11, 7), 1.74f, "Bruno Costa", "Brasil", 9, 80, "bruno.jpg", new PlayerPosition("ME"), PreferredFoot.Both, 78, 86, 87, 72.0f),
                new Player(30, LocalDate.of(1993, 10, 20), 1.89f, "Carlos Ramos", "Espanha", 10, 60, "carlosr.jpg", new PlayerPosition("PL"), PreferredFoot.Left, 91, 84, 75, 85.0f),
                new Player(27, LocalDate.of(1997, 12, 3), 1.83f, "Rui Mendes", "Moçambique", 11, 75, "rui.jpg", new PlayerPosition("PL"), PreferredFoot.Left, 87, 81, 77, 81.0f),
                new Player(21, LocalDate.of(2003, 4, 17), 1.76f, "Fábio Leite", "Portugal", 12, 60, "fabio.jpg", new PlayerPosition("DC"), PreferredFoot.Left, 55, 65, 78, 74.5f),
                new Player(28, LocalDate.of(1996, 8, 29), 1.88f, "André Castro", "Portugal", 13, 90, "andre.jpg", new PlayerPosition("MO"), PreferredFoot.Left, 85, 90, 86, 79.0f),
                new Player(25, LocalDate.of(1999, 2, 25), 1.84f, "Tiago Neves", "Portugal", 14, 66, "tiagoneves.jpg", new PlayerPosition("MC"), PreferredFoot.Left, 68, 75, 82, 77.0f),
                new Player(26, LocalDate.of(1998, 6, 1), 1.79f, "Eduardo Viana", "Brasil", 15, 78, "eduardo.jpg", new PlayerPosition("DD"), PreferredFoot.Left, 70, 78, 81, 76.0f),
                new Player(31, LocalDate.of(1993, 1, 13), 1.91f, "Henrique Lopes", "Portugal", 16, 55, "henrique.jpg", new PlayerPosition("GR"), PreferredFoot.Right, 40, 58, 74, 83.0f)
        };

        // Adicionar jogadores ao clube
        for (IPlayer jogador : jogadores) {
            club.addPlayer(jogador);
        }

        // Validar clube
        try {
            club.isValid();
            System.out.println("Clube válido com " + club.getPlayerCount() + " jogadores.");
        } catch (Exception e) {
            System.out.println("Erro ao validar clube: " + e.getMessage());
        }

        // Formação 4-4-2
        IFormation formacao442 = new IFormation() {
            @Override
            public String getDisplayName() {
                return "4-4-2";
            }

            @Override
            public int getTacticalAdvantage(IFormation other) {
                return 0;
            }
        };

        Team team = new Team(club, formacao442);

        // Posições a preencher
        String[] posicoes = {"GR", "DD", "DC", "DC", "DE", "MD", "MC", "MC", "ME", "PL", "PL"};
        IPlayer[] jaEscolhidos = new IPlayer[11];
        int escolhidos = 0;

        for (String desc : posicoes) {
            IPlayerPosition posicao = new PlayerPosition(desc);
            IPlayer escolhido = null;

            IPlayer[] candidatos = club.getPlayers();
            for (IPlayer candidato : candidatos) {
                if (candidato.getPosition().equals(posicao)) {
                    // Verifica se já foi usado
                    boolean jaUsado = false;
                    for (int i = 0; i < escolhidos; i++) {
                        if (jaEscolhidos[i] == candidato) {
                            jaUsado = true;
                            break;
                        }
                    }
                    if (!jaUsado) {
                        escolhido = candidato;
                        break;
                    }
                }
            }

            if (escolhido == null) {
                throw new IllegalStateException("Nenhum jogador disponível para a posição: " + desc);
            }

            team.addPlayer(escolhido);
            jaEscolhidos[escolhidos++] = escolhido;
        }

        // Mostrar equipa
        System.out.println("\nEquipa Titular:");
        for (IPlayer j : team.getPlayers()) {
            System.out.println("- " + j.getName() + " | " + j.getPosition().getDescription());
        }

        // Força
        System.out.println("\nForça média da equipa: " + team.getTeamStrength());
    }
}
