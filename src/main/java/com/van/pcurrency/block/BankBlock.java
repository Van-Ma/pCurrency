package com.van.pcurrency.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.van.pcurrency.coin.BankScreen;

import net.minecraft.client.Minecraft;

public class BankBlock extends Block {

    public BankBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            // Client-side
            Minecraft.getInstance().setScreen(new BankScreen());
            return InteractionResult.SUCCESS;
        } else {
            // Server-side
            return InteractionResult.SUCCESS;
        }
    }
}
