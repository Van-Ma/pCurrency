package com.van.pcurrency.events;

import com.van.pcurrency.PCurrencyMod;
import com.van.pcurrency.entities.ModEntities;
import com.van.pcurrency.entities.VillagerEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PCurrencyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.VILLAGER_ENTITY.get(), VillagerEntity.createAttributes().build());
    }
}
