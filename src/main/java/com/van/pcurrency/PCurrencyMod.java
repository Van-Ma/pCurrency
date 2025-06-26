package com.van.pcurrency;

import com.van.pcurrency.block.ModBlocks;
import com.van.pcurrency.item.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(PCurrencyMod.MODID)
public class PCurrencyMod {
    public static final String MODID = "pcurrency";

    public PCurrencyMod() {
   
        
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    
}
