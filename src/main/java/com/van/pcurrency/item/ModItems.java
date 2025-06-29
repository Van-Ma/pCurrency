package com.van.pcurrency.item;

import com.van.pcurrency.block.ModBlocks;  
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, "pcurrency");

    // Register coin
    public static final RegistryObject<Item> COIN = ITEMS.register("coin",
        () -> new CoinItem(new Item.Properties().stacksTo(64)));
         // Register bundle
    public static final RegistryObject<Item> BUNDLE = ITEMS.register("bundle",
        () -> new CoinItem(new Item.Properties().stacksTo(64)));

    // Register the bank block item
    public static final RegistryObject<Item> BANK_ITEM = ITEMS.register("bank_block",
        () -> new BankItem(ModBlocks.BANK_BLOCK.get(), new Item.Properties().stacksTo(64)));
}