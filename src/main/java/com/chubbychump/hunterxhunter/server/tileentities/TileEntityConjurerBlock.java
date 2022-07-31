package com.chubbychump.hunterxhunter.server.tileentities;

import com.chubbychump.hunterxhunter.server.blocks.ConjurerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.CONJURER_BLOCK_TILE_ENTITY;

public class TileEntityConjurerBlock extends BlockEntity implements BlockEntityTicker {
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
    public void tick(Level l, BlockPos blockPos, BlockState blockState, ConjurerBlock conjurerBlock) {
        if (deathTimer > 0) {
            deathTimer--;
        } else {
            this.remove();
            level.removeBlock(this.getBlockPos(), false);
        }
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundTag nbtTagCompound = new CompoundTag();
        write(nbtTagCompound);
        return new SUpdateTileEntityPacket(this.pos, 0, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        BlockState blockState = world.getBlockState(pos);
        read(blockState, pkt.getNbtCompound());   // read from the nbt in the packet
    }

    @Override
    public void handleUpdateTag(BlockState blockState, CompoundTag parentNBTTagCompound) {
        this.read(blockState, parentNBTTagCompound);
    }

    @Override
    public void read(BlockState state, CompoundTag nbt) {
        super.read(state, nbt);
        this.deathTimer = nbt.getInt("deathTimer");
    }

    @Override
    public CompoundTag write(CompoundTag compound) {
        super.write(compound);
        compound.putInt("deathTimer", deathTimer);
        return compound;
    }
}
