package com.chubbychump.hunterxhunter.common.tileentities;

import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.NEN_LIGHT_TILE_ENTITY;

public class TileEntityNenLight extends TileEntity implements ITickableTileEntity {
    private int deathTimer;
    public int levelOfLight;

    public TileEntityNenLight() {
        super(NEN_LIGHT_TILE_ENTITY.get());
        deathTimer = 20;
        levelOfLight = 0;
    }

    public void setLevelOfLight(int lightlevel) {
        levelOfLight = lightlevel;
    }

    @Override
    public void tick() {
        if (deathTimer != 0) {
            deathTimer--;
        } else {
            levelOfLight = 0;
            //world.removeTileEntity(pos);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }
}