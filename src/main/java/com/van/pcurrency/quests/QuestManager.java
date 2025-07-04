package com.van.pcurrency.quests;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    private static final List<Quest> QUESTS = new ArrayList<>();

    public static void loadQuests() {
        QUESTS.add(new Quest(1, "minecraft:spruce_log", 10, 5));
        QUESTS.add(new Quest(2, "minecraft:spruce_log", 100, 10));
        QUESTS.add(new Quest(3, "minecraft:stone", 50, 15));
        QUESTS.add(new Quest(4, "minecraft:iron_ingot", 20, 25));
        QUESTS.add(new Quest(5, "minecraft:diamond", 5, 50));
    }

    public static Quest getQuest(int id) {
        if (id > 0 && id <= QUESTS.size()) {
            return QUESTS.get(id - 1);
        }
        return null;
    }
}