package src;

/**
 * Data-only class containing the numerical stat block of each pokemon
 * Each member is public as accessors and mutators are redundant in a data-only class
 * - int hp
 * - int attack
 * - int defence
 * - int spAttack
 * - int spDefense
 * - int speed
 * 
 * @author Connor Petri
 * @see Pokemon
 */
public class StatBlock {
    // Accessors and mutators are redundant in a data-only class
    // This is my protest of Java's lack of structs
    public int hp;
    public int attack;
    public int defense;
    public int spAttack;
    public int spDefense;
    public int speed;

    public StatBlock(int hp, int attack, int defense, int spAttack, int spDefense, int speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAttack = spAttack;
        this.spDefense = spDefense;
        this.speed = speed;
    }
}
