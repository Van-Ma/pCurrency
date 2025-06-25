package com.van.pcurrency.shekels;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class BankData {
    private static final String BALANCE_KEY = "pcurrency_balance";

    public static int getBalance(Player player) {
        CompoundTag tag = player.getPersistentData();
        return tag.getInt(BALANCE_KEY);
    }

    public static void setBalance(Player player, int amount) {
        CompoundTag tag = player.getPersistentData();
        tag.putInt(BALANCE_KEY, Math.max(0, amount));
    }

    public static void addBalance(Player player, int amount) {
        setBalance(player, getBalance(player) + Math.max(0, amount));
    }

    public static void subtractBalance(Player player, int amount) {
        setBalance(player, getBalance(player) - Math.max(0, amount));
    }
}
