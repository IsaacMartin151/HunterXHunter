package com.chubbychump.hunterxhunter.common.blocks;

import com.chubbychump.hunterxhunter.common.tileentities.TileEntityNenLight;
import com.chubbychump.hunterxhunter.init.ModTileEntityTypes;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class NenLight extends Block {
    public NenLight() {
        super(Properties.create(Material.AIR).notSolid().doesNotBlockMovement().noDrops());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.NENLIGHT.create();
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos blockPos) {
        TileEntity bruh = world.getTileEntity(blockPos);
        if(bruh instanceof TileEntityNenLight){
            return ((TileEntityNenLight) bruh).levelOfLight;
        }
        else {
            return 0;
        }
    }

    public boolean isTransparent() {
        return true;
    }
}