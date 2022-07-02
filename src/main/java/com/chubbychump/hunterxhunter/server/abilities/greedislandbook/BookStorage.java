package com.chubbychump.hunterxhunter.server.abilities.greedislandbook;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public class BookStorage implements Capability.IStorage<ItemStackHandler> {
    @Override
    public INBT writeNBT(Capability<ItemStackHandler> capability, ItemStackHandler instance, Direction side) {
        CompoundNBT tag = instance.serializeNBT();
        return tag;
    }

    @Override
    public void readNBT(Capability<ItemStackHandler> capability, ItemStackHandler instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.deserializeNBT(tag);
    }
}
