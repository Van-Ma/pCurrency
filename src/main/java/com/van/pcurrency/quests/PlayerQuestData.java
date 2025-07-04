package com.van.pcurrency.quests;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

public class PlayerQuestData {
    private static final String QUEST_KEY = "pcurrency_quest_id";

    public static Quest getCurrentQuest(Player player) {
        CompoundTag tag = player.getPersistentData();
        int questId = tag.getInt(QUEST_KEY);
        if (questId == 0) {
            tag.putInt(QUEST_KEY, 1);
            questId = 1;
        }
        return QuestManager.getQuest(questId);
    }

    public static void advance(Player player) {
        CompoundTag tag = player.getPersistentData();
        int current = tag.getInt(QUEST_KEY);
        tag.putInt(QUEST_KEY, current + 1);
    }
}