package com.chubbychump.hunterxhunter.server.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.CONJURER_BLOCK_TILE_ENTITY;

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
        return CONJURER_BLOCK_TILE_ENTITY.get().create();

    }
}