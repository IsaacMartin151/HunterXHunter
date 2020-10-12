package com.chubbychump.hunterxhunter.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.NEN_LIGHT_TILE_ENTITY;

public class NenLight extends Block {
    public NenLight() {
        super(Block.Properties.create(Material.AIR)
                .func_235838_a_((lightLevel) -> 15));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return NEN_LIGHT_TILE_ENTITY.get().create();
    }

    public NenLight get() {
        return this;
    }
}