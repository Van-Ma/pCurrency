package com.van.pcurrency.coin;

import com.van.pcurrency.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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

 public static int getPocketBalance(Player player) {
    int coins = 0;
    int bundles = 0;

    for (ItemStack stack : player.getInventory().items) {
        if (stack.getItem() == ModItems.COIN.get()) {
            coins += stack.getCount();
        } else if (stack.getItem() == ModItems.BUNDLE.get()) {
            bundles += stack.getCount();
        }
    }

    return coins + (bundles * 64);
}


public static void addPocketBalance(Player player, int amount) {
    if (amount <= 0) return;

    Item coinItem = ModItems.COIN.get();

    int remaining = amount;

    // Add coins to existing stacks first
    for (ItemStack stack : player.getInventory().items) {
        if (stack.getItem() == coinItem) {
            int space = stack.getMaxStackSize() - stack.getCount();
            if (space > 0) {
                int toAdd = Math.min(space, remaining);
                stack.grow(toAdd);
                remaining -= toAdd;
                if (remaining <= 0) break;
            }
        }
    }

    // Add new stacks if still remaining coins
    while (remaining > 0) {
        int toAdd = Math.min(coinItem.getMaxStackSize(), remaining);
        ItemStack newStack = new ItemStack(coinItem, toAdd);
        boolean added = player.getInventory().add(newStack);
        if (!added) {
            player.drop(newStack, false);
        }
        remaining -= toAdd;
    }

    // Now convert all full coin stacks (64) to bundles
    convertCoinsToBundles(player);
}

public static void convertCoinsToBundles(Player player) {
    Item coinItem = ModItems.COIN.get();
    Item bundleItem = ModItems.BUNDLE.get();

    for (int i = 0; i < player.getInventory().items.size(); i++) {
        ItemStack stack = player.getInventory().items.get(i);
        if (stack.getItem() == coinItem && stack.getCount() >= 64) {
            int coinCount = stack.getCount();

            int bundles = coinCount / 64;
            int leftover = coinCount % 64;

            // Replace current stack with leftover coins (or empty if none)
            if (leftover > 0) {
                stack.setCount(leftover);
            } else {
                player.getInventory().items.set(i, ItemStack.EMPTY);
            }

            // Add bundles to inventory (or drop if inventory full)
            ItemStack bundleStack = new ItemStack(bundleItem, bundles);
            boolean added = player.getInventory().add(bundleStack);
            if (!added) {
                player.drop(bundleStack, false);
            }
        }
    }
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

   public static void depositToBank(Player player, int amount) {
    deposit(player, amount); // Uses your safe deposit method already defined
}
}


