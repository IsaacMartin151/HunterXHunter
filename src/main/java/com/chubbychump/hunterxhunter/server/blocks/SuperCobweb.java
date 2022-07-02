package com.chubbychump.hunterxhunter.server.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.SUPER_COBWEB_ITEM;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.SUPER_STRING;

public class SuperCobweb extends Block implements net.minecraftforge.common.IForgeShearable {
    public SuperCobweb(AbstractBlock.Properties properties) {
        super(properties);
        this.makeCuboidShape(.1, .1, .1, .15, .15, .15);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotionMultiplier(state, new Vector3d(0.25D, (double)0.05F, 0.25D));
    }

    @Override
    public boolean isVariableOpacity() {
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        worldIn.playEvent(player, 2001, pos, getStateId(state));
        if (player.getHeldItemMainhand().getItem() == Items.SHEARS) {
            worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), SUPER_COBWEB_ITEM.get().getDefaultInstance()));
        }
        if (player.getHeldItemMainhand().getItem() instanceof SwordItem) {
            worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), SUPER_STRING.get().getDefaultInstance()));
        }

    }
}