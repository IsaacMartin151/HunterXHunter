package com.abilities.greedislandbook;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class GreedIslandProvider implements ICapabilitySerializable<CompoundTag> {

    @CapabilityInject(ItemStackHandler.class)
    public static final Capability<ItemStackHandler> BOOK_CAPABILITY = null;

    private LazyOptional<ItemStackHandler> instance = LazyOptional.of(BOOK_CAPABILITY::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == BOOK_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return (CompoundTag) BOOK_CAPABILITY.getStorage().writeNBT(BOOK_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        BOOK_CAPABILITY.getStorage().readNBT(BOOK_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}