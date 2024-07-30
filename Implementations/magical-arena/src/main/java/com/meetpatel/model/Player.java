package com.meetpatel.model;

import java.util.Objects;

/**
 * Represents a Player in the Magical Arena game.
 * This class encapsulates user details such as id, name, health, attack, and strength of a player.
 */
public class Player {
    private final String id;
    private String name;
    private int health;
    private final int attack;
    private final int strength;
    private boolean isAlive;

    public Player(String id, String name, int health, int attack, int strength) {
        // Check for invalid (negative) values for health, attack, or strength.
        if (health <= 0 || attack <= 0 || strength <= 0) {
            throw new IllegalArgumentException("Health, Attack, and Strength must be positive integers.");
        }
        this.id = id;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.strength = strength;
        this.isAlive = true;
    }

    /**
     * @param rolledValue: value rolled by the player with attacking dice
     * @return the damage value of this attacker based on the rolledValue
     */
    public long getAttackDamage(int rolledValue) {
        return (long) this.getAttack() * rolledValue;
    }

    /**
     * @param rolledValue: value rolled by the player with defending dice
     * @return the defense strength of this defender based on the rolledValue
     */
    public long getDefenseStrength(int rolledValue) {
        return (long) this.getStrength() * rolledValue;
    }

    /**
     * Applies the given damage value to this player's health, and updates isAlive accordingly.
     * @param damage: the net damage to be applied to this player
     * Net Damage = attacker's attack value - this player's defence strength value
     */
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public int getStrength() {
        return strength;
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name)
                && Objects.equals(health, player.health)
                && Objects.equals(attack, player.attack)
                && Objects.equals(strength, player.strength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, health, attack, strength);
    }
}
