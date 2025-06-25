package com.van.pcurrency.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks; 
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, "pcurrency");
        
public static final RegistryObject<Block> BANK_BLOCK = BLOCKS.register("bank_block",
    () -> new BankBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
}