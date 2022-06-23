package com.chubbychump.hunterxhunter.common.blocks;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.URAWIZARD;

public class UrAWizardHarry extends AbstractButtonBlock {
    public UrAWizardHarry() {
        super(false, Properties.create(Material.ROCK).notSolid().doesNotBlockMovement().variableOpacity());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        player.addVelocity(3.0, 3.0, 3.0);
        player.playSound(URAWIZARD.get(), 1, 1);
        if (state.get(POWERED)) {
            return ActionResultType.CONSUME;
        } else {
            //this.powerBlock(state, worldIn, pos);
            //this.playSound(player, worldIn, pos, true);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

    }

    @Override
    protected SoundEvent getSoundEvent(boolean isOn) {
        return null;
    }
}
