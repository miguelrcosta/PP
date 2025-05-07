/*
 * Nome: João Exemplo
 * Número: 123456
 * Turma: LEI-PL1
 *
 * Nome: Maria Exemplo
 * Número: 654321
 * Turma: LEI-PL1
 */

package org.example;

import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import org.example.player.Player;
import org.example.player.PlayerPosition;
import org.example.team.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        PlayerPosition gk = new PlayerPosition("GK");
        PlayerPosition st = new PlayerPosition("ST");
        PlayerPosition cb = new PlayerPosition("CB");
        PlayerPosition cm = new PlayerPosition("CM");

        // Criar clubes
        Club club1 = new Club("SLB", "Portugal", 1904, "logo1.png", "Benfica", "Estádio da Luz");
        Club club2 = new Club("SCP", "Portugal", 1906, "logo2.png", "Sporting", "Estádio José Alvalade");

        // Criar jogadores e adicionar ao clube1
        for (int i = 1; i <= 17; i++) {
            Player player = new Player(22 + i % 5, LocalDate.of(2000, 1, i), 1.75f + i % 5,
                    "Benfica Player " + i, "Portugal", i, 60 + i % 10, "",
                    (i == 1) ? gk : ((i % 3 == 0) ? cb : st),
                    PreferredFoot.Left, 65 + i % 10, 70 + i % 10, 75 + i % 10, 70 + i);
            club1.addPlayer(player);
        }

        // Criar jogadores e adicionar ao clube2
        for (int i = 1; i <= 17; i++) {
            Player player = new Player(20 + i % 5, LocalDate.of(2001, 2, i), 1.70f + i % 5,
                    "Sporting Player " + i, "Portugal", i, 55 + i % 10, "",
                    (i == 1) ? gk : ((i % 3 == 0) ? cb : cm),
                    PreferredFoot.Right, 60 + i % 10, 65 + i % 10, 70 + i % 10, 68 + i);
            club2.addPlayer(player);
        }

        // Testar isValid()
        try {
            System.out.println("Benfica válido? " + club1.isValid());
            System.out.println("Sporting válido? " + club2.isValid());
        } catch (Exception e) {
            System.out.println("Erro ao validar clubes: " + e.getMessage());
        }

        // Criar equipas e formação
        Formation formation442 = new Formation("4-4-2");
        Team team1 = new Team(club1, formation442);
        Team team2 = new Team(club2, formation442);

        // Adicionar jogadores às equipas
        for (int i = 0; i < 11; i++) {
            team1.addPlayer(club1.getPlayers()[i]);
            team2.addPlayer(club2.getPlayers()[i]);
        }

        // Testar getTeamStrength()
        System.out.println("Força média Benfica: " + team1.getTeamStrength());
        System.out.println("Força média Sporting: " + team2.getTeamStrength());

        // Testar getPositionCount()
        int benficaSTCount = team1.getPositionCount(st);
        int sportingCMCount = team2.getPositionCount(cm);
        System.out.println("Benfica - Quantidade de ST: " + benficaSTCount);
        System.out.println("Sporting - Quantidade de CM: " + sportingCMCount);

        // Testar isValidPositionForFormation()
        System.out.println("Posição GK é válida no 4-4-2? " + team1.isValidPositionForFormation(gk));
        System.out.println("Posição CM é válida no 4-4-2? " + team1.isValidPositionForFormation(cm));

        // Testar setFormation()
        Formation formation352 = new Formation("3-5-2");
        team1.setFormation(formation352);
        System.out.println("Nova formação do Benfica: " + team1.getFormation().getDisplayName());

        // Testar seleção de jogador por posição
        PlayerSelector selector = new PlayerSelector();
        try {
            System.out.println("Selecionando GK no Benfica: " + selector.selectPlayer(club1, gk).getName());
            System.out.println("Selecionando CB no Sporting: " + selector.selectPlayer(club2, cb).getName());
        } catch (Exception e) {
            System.out.println("Erro ao selecionar jogador: " + e.getMessage());
        }

        // Testar exceção de clube inválido (menos de 16 jogadores)
        Club smallClub = new Club("MIN", "Portugal", 1990, "logo3.png", "Mini Club", "Mini Stadium");
        smallClub.addPlayer(new Player(20, LocalDate.of(2005, 1, 1), 1.80f, "Mini Player 1", "Portugal", 1, 50, "", gk, PreferredFoot.Left, 40, 50, 60, 65));
        try {
            System.out.println("Mini Club válido? " + smallClub.isValid());
        } catch (Exception e) {
            System.out.println("Erro esperado ao validar Mini Club: " + e.getMessage());
        }
    }
}
