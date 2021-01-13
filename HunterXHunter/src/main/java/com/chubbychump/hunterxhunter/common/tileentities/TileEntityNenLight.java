package com.chubbychump.hunterxhunter.common.tileentities;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

import static com.chubbychump.hunterxhunter.init.ModTileEntityTypes.NENLIGHT;

public class TileEntityNenLight extends TileEntity implements ITickableTileEntity {
    private int deathTimer = 40;
    public int levelOfLight = 0;

    public TileEntityNenLight() {
        super(NENLIGHT);
        this.deathTimer = 40;
    }

    @Override
    public TileEntityNenLight getTileEntity() {
        return this;
    }

    public void setLevelOfLight(int lightlevel) {
        levelOfLight = lightlevel;
    }

    @Override
    public void tick() {
        if (deathTimer > 0) {
            deathTimer--;
        } else {
            this.remove();
            world.removeBlock(this.pos, false);
        }
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        return new SUpdateTileEntityPacket(this.pos, 0, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        BlockState blockState = world.getBlockState(pos);
        read(blockState, pkt.getNbtCompound());   // read from the nbt in the packet
    }

    @Override
    public void handleUpdateTag(BlockState blockState, CompoundNBT parentNBTTagCompound) {
        this.read(blockState, parentNBTTagCompound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.deathTimer = nbt.getInt("deathTimer");
        this.levelOfLight = nbt.getInt("leveloflight");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("deathTimer", deathTimer);
        compound.putInt("leveloflight", levelOfLight);
        return compound;
    }
}