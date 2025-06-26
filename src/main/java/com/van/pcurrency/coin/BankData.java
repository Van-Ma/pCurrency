package com.van.pcurrency.coin;

import com.van.pcurrency.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BankData {
    private static final String BALANCE_KEY = "pcurrency_balance";

    // Gets the internal bank balance (stored with player data)
    public static int getBalance(Player player) {
        CompoundTag tag = player.getPersistentData();
        return tag.getInt(BALANCE_KEY);
    }

    // Alias for getBalance, for clarity when used in UI
    public static int getBankBalance(Player player) {
        return getBalance(player);
    }

    // Gets how many coins the player has in inventory
    public static int getPocketBalance(Player player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == ModItems.COIN.get()) {
                count += stack.getCount();
            }
        }
        return count;
    }

    // Sets the player's bank balance
    public static void setBalance(Player player, int amount) {
        CompoundTag tag = player.getPersistentData();
        tag.putInt(BALANCE_KEY, Math.max(0, amount));
    }

    // Adds coins to the player's bank balance
    public static void addBalance(Player player, int amount) {
        setBalance(player, getBalance(player) + Math.max(0, amount));
    }

    // Subtracts coins from the player's bank balance
    public static void subtractBalance(Player player, int amount) {
        setBalance(player, getBalance(player) - Math.max(0, amount));
    }

    // deposit coins from inventory to bank
    public static boolean deposit(Player player, int amount) {
        int pocket = getPocketBalance(player);
        if (amount > 0 && amount <= pocket) {
            subtractCoinsFromInventory(player, amount);
            addBalance(player, amount);
            return true;
        }
        return false;
    }

    // withdraw coins from bank to inventory
    public static boolean withdraw(Player player, int amount) {
        int bank = getBalance(player);
        if (amount > 0 && amount <= bank) {
            subtractBalance(player, amount);
            giveCoinsToPlayer(player, amount);
            return true;
        }
        return false;
    }

    //  remove coins from player's inventory
    private static void subtractCoinsFromInventory(Player player, int amount) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == ModItems.COIN.get()) {
                int remove = Math.min(stack.getCount(), amount);
                stack.shrink(remove);
                amount -= remove;
                if (amount <= 0) break;
            }
        }
    }

    // add coins to player's inventory or drop them if full
    private static void giveCoinsToPlayer(Player player, int amount) {
        ItemStack coins = new ItemStack(ModItems.COIN.get(), amount);
        if (!player.getInventory().add(coins)) {
            player.drop(coins, false); // drop if inventory full
        }
    }
}
