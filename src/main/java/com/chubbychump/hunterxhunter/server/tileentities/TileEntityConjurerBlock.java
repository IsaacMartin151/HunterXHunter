package com.chubbychump.hunterxhunter.server.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.CONJURER_BLOCK_TILE_ENTITY;

public class TileEntityConjurerBlock extends TileEntity implements ITickableTileEntity {
    private int deathTimer = 100;

    public TileEntityConjurerBlock() {
        super(CONJURER_BLOCK_TILE_ENTITY.get());
        this.deathTimer = 40;
    }

    @Override
    public TileEntityConjurerBlock getTileEntity() {
        return this;
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
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("deathTimer", deathTimer);
        return compound;
    }
}
