package com.van.pcurrency.events;

import com.van.pcurrency.PCurrencyMod;
import com.van.pcurrency.entities.FishermanVillagerEntity;
import com.van.pcurrency.entities.ModEntities;
import com.van.pcurrency.entities.QuestVillagerEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PCurrencyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.FISHERMAN.get(), FishermanVillagerEntity.createAttributes().build());
        event.put(ModEntities.QUEST_VILLAGER.get(), QuestVillagerEntity.createAttributes().build());
    }
}
