package org.example.player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.IOException;
import java.time.LocalDate;

public class Player implements IPlayer {
    private int age;
    private LocalDate birthDate;
    private float height;
    private String name;
    private String nationality;
    private int number;
    private int passing;
    private String photo;
    private IPlayerPosition position;
    private PreferredFoot preferredFoot;
    private int shooting;
    private int speed;
    private int stamina;
    private float weight;

    public Player(int age, LocalDate birthDate, float height, String name, String nationality, int number, int passing, String photo, IPlayerPosition position, PreferredFoot preferredFoot, int shooting, int speed, int stamina, float weight) {
        this.age = age;
        this.birthDate = birthDate;
        this.height = height;
        this.name = name;
        this.nationality = nationality;
        this.number = number;
        this.passing = passing;
        this.photo = photo;
        this.position = position;
        this.preferredFoot = preferredFoot;
        this.shooting = shooting;
        this.speed = speed;
        this.stamina = stamina;
        this.weight = weight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public void setPosition(IPlayerPosition iPlayerPosition) {
        if (iPlayerPosition == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = iPlayerPosition;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getShooting() {
        return shooting;
    }

    @Override
    public int getPassing() {
        return passing;
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public IPlayerPosition getPosition() {
        return position;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public PreferredFoot getPreferredFoot() {
        return preferredFoot;
    }

    @Override
    public void exportToJson() throws IOException {

    }
}
