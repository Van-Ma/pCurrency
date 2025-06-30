package com.van.pcurrency;

import com.van.pcurrency.block.ModBlocks;
import com.van.pcurrency.item.ModItems;
import net.minecraftforge.fml.common.Mod;
<<<<<<< HEAD
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(PCurrencyMod.MODID)
public class PCurrencyMod {
    public static final String MODID = "pcurrency";
=======
import com.van.pcurrency.entities.ModEntities;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(PCurrencyMod.MOD_ID)
public class PCurrencyMod {
    public static final String MOD_ID = "pcurrency";
>>>>>>> ec82b245e2930de712c40bc1537a560b96d5c915

    public PCurrencyMod() {
   
        
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
<<<<<<< HEAD
=======
        ModEntities.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());


>>>>>>> ec82b245e2930de712c40bc1537a560b96d5c915
    }
    
}
