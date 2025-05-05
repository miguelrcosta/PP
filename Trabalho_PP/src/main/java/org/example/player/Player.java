package org.example.player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.IOException;
import java.time.LocalDate;

public class Player implements IPlayer {
    private String name;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public LocalDate getBirthDate() {
        return null;
    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public String getNationality() {
        return "";
    }

    @Override
    public void setPosition(IPlayerPosition iPlayerPosition) {

    }

    @Override
    public String getPhoto() {
        return "";
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public int getShooting() {
        return 0;
    }

    @Override
    public int getPassing() {
        return 0;
    }

    @Override
    public int getStamina() {
        return 0;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public IPlayerPosition getPosition() {
        return null;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public PreferredFoot getPreferredFoot() {
        return null;
    }

    @Override
    public void exportToJson() throws IOException {

    }
}
