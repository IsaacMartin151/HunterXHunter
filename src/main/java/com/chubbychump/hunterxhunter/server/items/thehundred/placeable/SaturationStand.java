package com.chubbychump.hunterxhunter.server.items.thehundred.placeable;

import com.chubbychump.hunterxhunter.server.tileentities.SaturationStandTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;


public class SaturationStand extends Block {
    public SaturationStand() {
        super(Block.Properties.of(Material.METAL)
                .strength(1.0f, 1.0f)
                .sound(SoundType.METAL));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockEntity createTileEntity(BlockState blockstate, IBlockReader worldIn) {
        return new SaturationStandTileEntity();
    }

}
