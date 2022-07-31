package com.chubbychump.hunterxhunter.server.abilities.greedislandbook;

import com.chubbychump.hunterxhunter.server.abilities.nenstuff.INenUser;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class GreedIslandProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<ItemStackHandler> BOOK_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    private LazyOptional<ItemStackHandler> instance = LazyOptional.of(BookItemStackHandler::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == BOOK_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        INenUser nenUser = instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
        return nenUser.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        INenUser nenUser = instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
        nenUser.deserializeNBT(nbt);
    }
}