package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.IOException;
import java.time.LocalDate;

public class Player implements IGoalKeeper {
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
    private int reflexes;
    private int diving;
    private int handling;

    public Player(int age, LocalDate birthDate, float height, String name, String nationality, int number, int passing, String photo, IPlayerPosition position, PreferredFoot preferredFoot, int shooting, int speed, int stamina, float weight, int reflexes, int diving, int handling) {
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
        this.reflexes = reflexes;
        this.diving = diving;
        this.handling = handling;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public String getNationality() {
        return this.nationality;
    }

    @Override
    public void setPosition(IPlayerPosition iPlayerPosition) {
        if (iPlayerPosition == null) {
            throw new IllegalArgumentException("Position can't be null");
        }
        this.position = iPlayerPosition;
    }

    @Override
    public String getPhoto() {
        return this.photo;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public int getShooting() {
        return this.shooting;
    }

    @Override
    public int getPassing() {
        return this.passing;
    }

    @Override
    public int getStamina() {
        return this.stamina;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public IPlayerPosition getPosition() {
        return this.position;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    public int getReflexes() {
        return this.reflexes;
    }

    public int getDiving() {
        return this.diving;
    }

    public int getHandling() {
        return this.handling;
    }

    @Override
    public PreferredFoot getPreferredFoot() {
        return this.preferredFoot;
    }

    @Override
    public void exportToJson() throws IOException {
        // Implementar mais tarde
    }
}