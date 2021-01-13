package com.chubbychump.hunterxhunter.common.blocks;

import com.chubbychump.hunterxhunter.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ConjurerBlock extends Block {
    public ConjurerBlock() {
        super(Properties.create(Material.EARTH).noDrops());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.CONJURER_TILE_ENTITY.create();

    }
}