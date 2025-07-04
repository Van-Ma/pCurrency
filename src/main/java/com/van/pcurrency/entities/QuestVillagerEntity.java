package com.van.pcurrency.entities;

import com.van.pcurrency.coin.BankData;
import com.van.pcurrency.quests.Quest;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class QuestVillagerEntity extends Villager {

    public QuestVillagerEntity(EntityType<? extends Villager> type, Level level) {
        super(type, level);
        this.setNoAi(true); // Prevent AI movement
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Villager.createAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Override
public InteractionResult mobInteract(Player player, InteractionHand hand) {
    if (!level().isClientSide && hand == InteractionHand.MAIN_HAND) {

        // Get current quest ID from player persistent data
        CompoundTag data = player.getPersistentData();
        int currentQuestId = data.getInt("currentQuestId");
        if (currentQuestId <= 0) currentQuestId = 1;  // default start quest 1

        // Get the quest based on currentQuestId (you need a method to fetch quests by ID)
        Quest quest = Quest.getQuestById(currentQuestId);
        if (quest == null) {
            player.sendSystemMessage(Component.literal("No quest available."));
            return InteractionResult.SUCCESS;
        }

        String itemId = quest.type;
        int amount = quest.amount;
        int reward = quest.reward;

        int total = countItem(player, itemId);

        if (total >= amount) {
            removeItems(player, itemId, amount);
            BankData.addPocketBalance(player, reward);
            player.sendSystemMessage(Component.literal("Quest complete! Reward: " + reward + " coins."));

            // Advance quest ID and save to player data
            data.putInt("currentQuestId", currentQuestId + 1);
        } else {
            player.sendSystemMessage(Component.literal("Bring " + amount + " of " + itemId));
        }

        return InteractionResult.SUCCESS;
    }

    return super.mobInteract(player, hand);
}

    private int countItem(Player player, String itemId) {
        Item questItem = getItemFromString(itemId);
        if (questItem == null) return 0;

        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == questItem) {
                count += stack.getCount();
            }
        }
        return count;
    }

    private void removeItems(Player player, String itemId, int amount) {
        Item questItem = getItemFromString(itemId);
        if (questItem == null) return;

        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == questItem) {
                int remove = Math.min(amount, stack.getCount());
                stack.shrink(remove);
                amount -= remove;
                if (amount <= 0) break;
            }
        }
    }

    private Item getItemFromString(String itemId) {
        if (itemId == null || itemId.isEmpty()) return null;
        ResourceLocation resourceLocation = ResourceLocation.tryParse(itemId);
        if (resourceLocation == null) return null;
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    // Disable AI goals
    @Override
    protected void registerGoals() {}

    // Don't move at all
    @Override
    public void travel(Vec3 travelVector) {}

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public void push(Entity entity) {}

    @Override
    public void push(double x, double y, double z) {}
}
