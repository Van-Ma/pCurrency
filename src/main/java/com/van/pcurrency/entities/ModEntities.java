package com.van.pcurrency.entities;

import com.van.pcurrency.PCurrencyMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PCurrencyMod.MOD_ID);

    public static final RegistryObject<EntityType<VillagerEntity>> VILLAGER_ENTITY =
            ENTITY_TYPES.register("villager_entity", () ->
                    EntityType.Builder.<VillagerEntity>of(VillagerEntity::new, MobCategory.MISC)
                            .sized(0.6f, 1.95f) // Standard villager size
                            .build("villager_entity"));
}
