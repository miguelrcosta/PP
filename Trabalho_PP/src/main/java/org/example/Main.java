package org.example;

import org.example.player.Player;
import org.example.player.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Criar posição do jogador
        IPlayerPosition position = new PlayerPosition("Attacking forward");

        // Criar jogador
        Player player = new Player(
                25,
                LocalDate.of(1999, 5, 10),
                1.80f,
                "Cristiano Test",
                "Portugal",
                7,
                85,
                "photo.png",
                position,
                PreferredFoot.Right,
                90,
                88, // speed
                92, // stamina
                75.0f // weight
        );

        // Imprimir informações do jogador
        System.out.println("=== TESTE DO PLAYER ===");
        System.out.println("Nome: " + player.getName());
        System.out.println("Idade: " + player.getAge());
        System.out.println("Data de nascimento: " + player.getBirthDate());
        System.out.println("Altura: " + player.getHeight());
        System.out.println("Peso: " + player.getWeight());
        System.out.println("Nacionalidade: " + player.getNationality());
        System.out.println("Número: " + player.getNumber());
        System.out.println("Passe: " + player.getPassing());
        System.out.println("Remate: " + player.getShooting());
        System.out.println("Velocidade: " + player.getSpeed());
        System.out.println("Resistência: " + player.getStamina());
        System.out.println("Foto: " + player.getPhoto());
        System.out.println("Descrição da posição: " + player.getPosition().getDescription());
        System.out.println("Pé preferido: " + player.getPreferredFoot());

        // Testar setPosition()
        IPlayerPosition newPosition = new PlayerPosition("Central midfielder controlling the game");
        player.setPosition(newPosition);
        System.out.println("Nova descrição da posição: " + player.getPosition().getDescription());
    }
}
