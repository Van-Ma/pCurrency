package com.van.pcurrency.entities;

import com.van.pcurrency.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FishermanVillagerEntity extends Villager implements MenuProvider {

    private final MerchantOffers offers = new MerchantOffers();
    private Player tradingPlayer;

    public FishermanVillagerEntity(EntityType<? extends Villager> type, Level level) {
        super(type, level);
        this.setVillagerData(new VillagerData(VillagerType.PLAINS, VillagerProfession.FISHERMAN, 99));
        this.setNoAi(true);  // Disable AI so the villager won't move or pathfind
    }


@Override
public MerchantOffers getOffers() {
    if (offers.isEmpty()) {
        offers.clear();
        offers.add(new MerchantOffer(
            new ItemStack(Items.EMERALD, 5),
            new ItemStack(Items.COD, 1), 10, 10, 0.05F));
        
        offers.add(new MerchantOffer(
            new ItemStack(Items.EMERALD, 10),
            new ItemStack(Items.SALMON, 1), 10, 10, 0.05F));
        
        offers.add(new MerchantOffer(
            new ItemStack(Items.EMERALD, 5),
            new ItemStack(Items.FISHING_ROD, 1), 5, 10, 0.05F));
    }
    return offers;
}

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (player instanceof ServerPlayer serverPlayer) {
                this.setTradingPlayer(serverPlayer);
                serverPlayer.openMenu(this); // Open merchant UI
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    // Disable AI goals to prevent movement
    @Override
    protected void registerGoals() {
        // No AI goals = no movement or pathfinding
    }

    // Prevent movement by overriding travel method
    @Override
    public void travel(Vec3 travelVector) {
        // Do nothing so entity doesn't move
    }

    // Prevent being leashed by players
    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    // Make the entity not pushable by others
    @Override
    public boolean isPushable() {
        return false;
    }

    // Prevent movement caused by fluids (water, lava)
    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    // Override push to prevent entity being pushed by others
    @Override
    public void push(Entity entity) {
        // Do nothing - no pushing
    }

    // Override push with coordinates - do nothing
    @Override
    public void push(double x, double y, double z) {
        // Do nothing - no pushing
    }


    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {}

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    // MenuProvider interface
    @Override
    public Component getDisplayName() {
        return Component.literal("Fisherman");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, net.minecraft.world.entity.player.Inventory inventory, Player player) {
        return new MerchantMenu(id, inventory, this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Villager.createAttributes();
    }
}
