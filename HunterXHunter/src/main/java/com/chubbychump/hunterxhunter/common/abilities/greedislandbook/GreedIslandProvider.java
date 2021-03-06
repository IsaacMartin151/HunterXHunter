package com.chubbychump.hunterxhunter.common.abilities.greedislandbook;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class GreedIslandProvider implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(ItemStackHandler.class)
    public static final Capability<ItemStackHandler> BOOK_CAPABILITY = null;

    private LazyOptional<ItemStackHandler> instance = LazyOptional.of(BOOK_CAPABILITY::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == BOOK_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) BOOK_CAPABILITY.getStorage().writeNBT(BOOK_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        BOOK_CAPABILITY.getStorage().readNBT(BOOK_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}