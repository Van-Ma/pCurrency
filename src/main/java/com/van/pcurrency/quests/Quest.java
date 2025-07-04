package com.van.pcurrency.quests;

public class Quest {
    public int id;
    public String type;
    public int amount;
    public int reward;
    public String title;

    // Constructor
    public Quest(int id, String type, int amount, int reward) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.reward = reward;
        this.title = type.substring(type.indexOf(':') + 1); // simple title from item type (optional)
    }

    // Static method to get quests by id
    public static Quest getQuestById(int id) {
        switch(id) {
            case 1: return new Quest(1, "minecraft:apple", 5, 5);
            case 2: return new Quest(2, "minecraft:carrot", 10, 10);
            case 3: return new Quest(3, "minecraft:bread", 3, 30);
            // add more quests as needed
            default: return null; // no more quests
        }
    }
}