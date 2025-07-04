package com.van.pcurrency;

import com.van.pcurrency.block.ModBlocks;
import com.van.pcurrency.item.ModItems;
import com.van.pcurrency.quests.QuestManager;

import net.minecraftforge.fml.common.Mod;
import com.van.pcurrency.entities.ModEntities;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(PCurrencyMod.MOD_ID)
public class PCurrencyMod {
    public static final String MOD_ID = "pcurrency";

    public PCurrencyMod() {
   
        
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        QuestManager.loadQuests(); 

    }
    
}