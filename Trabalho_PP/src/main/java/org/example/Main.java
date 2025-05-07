package org.example;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;
import org.example.player.Player;
import org.example.player.PlayerPosition;
import org.example.team.Club;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Criar posições
        IPlayerPosition goalkeeperPos = new PlayerPosition("Goalkeeper");
        IPlayerPosition defenderPos = new PlayerPosition("Defender");
        IPlayerPosition midfielderPos = new PlayerPosition("Midfielder");
        IPlayerPosition forwardPos = new PlayerPosition("Forward");

        // Criar clube
        Club club = new Club("SCP", "Portugal", 1906, "scp-logo.png", "Sporting CP", "Estádio José Alvalade");

        // Adicionar 2 goalkeepers
        for (int i = 1; i <= 2; i++) {
            club.addPlayer(new Player(28, LocalDate.of(1995, 1, i), 1.90f, "Goalkeeper" + i, "Portugal", i, 60, "gk.png", goalkeeperPos, PreferredFoot.Right, 10, 50, 90, 85.0f));
        }

        // Adicionar 5 defenders
        for (int i = 3; i <= 7; i++) {
            club.addPlayer(new Player(26, LocalDate.of(1997, 2, i), 1.85f, "Defender" + i, "Portugal", i, 70, "def.png", defenderPos, PreferredFoot.Left, 40, 70, 80, 80.0f));
        }

        // Adicionar 5 midfielders
        for (int i = 8; i <= 12; i++) {
            club.addPlayer(new Player(24, LocalDate.of(1999, 3, i), 1.78f, "Midfielder" + i, "Portugal", i, 80, "mid.png", midfielderPos, PreferredFoot.Right, 60, 75, 85, 75.0f));
        }

        // Adicionar 5 forwards
        for (int i = 13; i <= 17; i++) {
            club.addPlayer(new Player(23, LocalDate.of(2000, 4, i), 1.80f, "Forward" + i, "Portugal", i, 85, "fwd.png", forwardPos, PreferredFoot.Right, 85, 90, 80, 70.0f));
        }

        // Implementar selector simples: devolve o primeiro jogador da posição
        IPlayerSelector selector = new IPlayerSelector() {
            @Override
            public IPlayer selectPlayer(com.ppstudios.footballmanager.api.contracts.team.IClub club, IPlayerPosition position) {
                for (IPlayer p : club.getPlayers()) {
                    if (p != null && p.getPosition().getDescription().equalsIgnoreCase(position.getDescription())) {
                        return p;
                    }
                }
                throw new IllegalStateException("No player found for the specified position.");
            }
        };

        // Selecionar jogador da posição "Midfielder"
        IPlayer selectedPlayer = club.selectPlayer(selector, midfielderPos);
        System.out.println("Jogador selecionado (Midfielder): " + selectedPlayer.getName());

        // Mostrar info do clube
        System.out.println("Clube: " + club.getName() + " (" + club.getCode() + ")");
        System.out.println("País: " + club.getCountry());
        System.out.println("Ano de fundação: " + club.getFoundedYear());
        System.out.println("Estádio: " + club.getStadiumName());
        System.out.println("Logo: " + club.getLogo());
        System.out.println("Número de jogadores: " + club.getPlayerCount());

        // Listar todos os jogadores
        System.out.println("\nLista de jogadores:");
        for (IPlayer p : club.getPlayers()) {
            if (p != null) {
                System.out.println("- " + p.getName() + " | Posição: " + p.getPosition().getDescription());
            }
        }

        // Validar o clube (deve passar!)
        try {
            club.isValid();
            System.out.println("\nClube válido ✅");
        } catch (IllegalStateException e) {
            System.out.println("\nValidação falhou ❌: " + e.getMessage());
        }
    }
}
