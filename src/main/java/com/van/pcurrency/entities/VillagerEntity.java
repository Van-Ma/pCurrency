package com.van.pcurrency.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class VillagerEntity extends Villager {

    public VillagerEntity(EntityType<? extends Villager> type, Level level) {
        super(type, level);
    }

       // VillagerEntity.java
public static AttributeSupplier.Builder createAttributes() {
    return net.minecraft.world.entity.npc.Villager.createAttributes();
}


    @Override
    protected void registerGoals() {
        // No AI goals = no movement, trading, or pathfinding
    }

    @Override
    public void travel(Vec3 travelVector) {
        // Prevent any movement
    }

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
    public void push(Entity entity) {
        // Do nothing
    }

    @Override
    public void push(double x, double y, double z) {
        // Do nothing
    }
}
