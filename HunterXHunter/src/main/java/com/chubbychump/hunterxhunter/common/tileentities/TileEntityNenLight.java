package com.chubbychump.hunterxhunter.common.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import static com.chubbychump.hunterxhunter.HunterXHunter.LOGGER;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.NEN_LIGHT_TILE_ENTITY;

public class TileEntityNenLight extends TileEntity implements ITickableTileEntity {
    //Light isn't going away - Need to set the block to air
    public Entity theEntity;
    private int deathTimer;
    public int levelOfLight;

    public TileEntityNenLight(TileEntityType<?> type) {
        super(type);
        LOGGER.info("Created new Tile entity, non-default constructor");
        deathTimer = 20;
        levelOfLight = 0;
    }

    public TileEntityNenLight() {
        this(NEN_LIGHT_TILE_ENTITY.get());
        LOGGER.info("Created new TileEntity, default constructor");
    }

    public void setLevelOfLight(int lightlevel) {
        levelOfLight = lightlevel;
        LOGGER.info("Setting level of light to "+levelOfLight);
    }

    @Override
    public TileEntityNenLight getTileEntity() {
        return this;
    }

    @Override
    public void tick() {
        LOGGER.info("In the tick cycle");
        if ((this.world.getGameTime() % 20) != 0) {
            deathTimer--;
            LOGGER.info("Reducing deathTimer, it is now " + deathTimer);
        } else {
            levelOfLight = 0;
            LOGGER.info("Killing off the tileentity and setting block to air");
            BlockState before = world.getBlockState(pos);
            world.removeTileEntity(pos);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            //world.destroyBlock(pos, false);
        }
    }

    /*
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("Light", this.levelOfLight);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.setLevelOfLight(compound.getInt("Light"));
    } */

}