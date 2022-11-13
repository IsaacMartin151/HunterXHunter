package com.abilities.greedislandbook;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class GreedIslandProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<BookItemStackHandler> BOOK_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
    private LazyOptional<BookItemStackHandler> instance = LazyOptional.of(BookItemStackHandler::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == BOOK_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        BookItemStackHandler itemStackHandler = instance.orElseThrow(() -> new IllegalArgumentException("Serialize BookItemStackHandler is empty!"));
        return itemStackHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        BookItemStackHandler nenUser = instance.orElseThrow(() -> new IllegalArgumentException("Deserialize BookItemStackHandler is empty!"));
        nenUser.deserializeNBT(nbt);
    }
}