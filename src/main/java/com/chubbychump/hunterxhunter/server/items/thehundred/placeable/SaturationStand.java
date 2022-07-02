package com.chubbychump.hunterxhunter.server.items.thehundred.placeable;

import com.chubbychump.hunterxhunter.server.tileentities.SaturationStandTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class SaturationStand extends Block {
    public SaturationStand() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.METAL)
                .harvestLevel(0));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState blockstate, IBlockReader worldIn) {
        return new SaturationStandTileEntity();
    }

}
