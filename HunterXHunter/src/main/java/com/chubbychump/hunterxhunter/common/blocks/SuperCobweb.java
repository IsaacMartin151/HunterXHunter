package com.chubbychump.hunterxhunter.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WebBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.SUPER_STRING;

public class SuperCobweb extends WebBlock {
    public SuperCobweb() {
        super(AbstractBlock.Properties.create(Material.WEB).doesNotBlockMovement().hardnessAndResistance(2.0F));
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        if (!worldIn.isRemote()) {
            worldIn.addEntity(new ItemEntity(worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10.0, false).getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), SUPER_STRING.get().getDefaultInstance()));
        }
    }
}