package com.chubbychump.hunterxhunter.common.blocks;

import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class NenLight extends Block {
    public NenLight() {
        super(Block.Properties.create(Material.AIR).doesNotBlockMovement().notSolid().noDrops());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityNenLight();
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos blockPos) {
        TileEntity bruh = world.getTileEntity(blockPos);
        if(bruh instanceof TileEntityNenLight){
            LOGGER.info("Found Tile Entity with light value " + ((TileEntityNenLight) bruh).levelOfLight);
            return ((TileEntityNenLight) bruh).levelOfLight;
        }
        else {
            LOGGER.info("Didn't find tile entity");
            return 0;
        }
    }
}