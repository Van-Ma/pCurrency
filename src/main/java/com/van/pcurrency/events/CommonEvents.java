package com.van.pcurrency.events;

import com.van.pcurrency.coin.BankData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "pcurrency")  // Replace with your mod id
public class CommonEvents {

 @SubscribeEvent
public static void onItemPickup(EntityItemPickupEvent event) {
    if (!(event.getEntity() instanceof Player)) return;

    Player player = (Player) event.getEntity();
    BankData.convertCoinsToBundles(player);
}
}