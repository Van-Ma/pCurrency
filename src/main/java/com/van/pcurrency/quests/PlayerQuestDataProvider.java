package com.van.pcurrency.quests;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerQuestDataProvider implements ICapabilityProvider {

    // Directly get the capability instance via CapabilityManager and CapabilityToken
    public static Capability<PlayerQuestData> PLAYER_QUEST_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    private final PlayerQuestData instance = new PlayerQuestData();

    private final LazyOptional<PlayerQuestData> optional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == PLAYER_QUEST_DATA ? optional.cast() : LazyOptional.empty();
    }
}
