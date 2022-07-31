package com.chubbychump.hunterxhunter.server.blocks;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.Player;
import net.minecraft.util.InteractionResultHolderType;
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
    public InteractionResultHolderType onBlockActivated(BlockState state, World worldIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit) {
        player.addVelocity(3.0, 3.0, 3.0);
        player.playSound(URAWIZARD.get(), 1, 1);
        if (state.get(POWERED)) {
            return InteractionResultHolderType.CONSUME;
        } else {
            //this.powerBlock(state, worldIn, pos);
            //this.playSound(player, worldIn, pos, true);
            return InteractionResultHolderType.func_233537_a_(worldIn.isClientSide);
        }

    }

    @Override
    protected SoundEvent getSoundEvent(boolean isOn) {
        return null;
    }
}
