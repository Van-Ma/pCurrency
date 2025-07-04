package com.van.pcurrency.entities;

import com.van.pcurrency.PCurrencyMod;
import com.van.pcurrency.entities.QuestVillagerEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PCurrencyMod.MOD_ID);

public static final RegistryObject<EntityType<FishermanVillagerEntity>> FISHERMAN =
    ENTITY_TYPES.register("pcurrency_fisherman",
        () -> EntityType.Builder.of(FishermanVillagerEntity::new, MobCategory.MISC)
            .sized(0.6F, 1.95F)
            .build("pcurrency_fisherman"));

 public static final RegistryObject<EntityType<QuestVillagerEntity>> QUEST_VILLAGER = ENTITY_TYPES.register("quest_villager",
        () -> EntityType.Builder.<QuestVillagerEntity>of(QuestVillagerEntity::new, MobCategory.MISC)
            .sized(0.6F, 1.95F)
            .build("quest_villager"));
}
