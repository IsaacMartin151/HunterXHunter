package com.abilities.greedislandbook;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public class BookStorage implements Capability.IStorage<ItemStackHandler> {
    @Override
    public Tag writeNBT(Capability<ItemStackHandler> capability, ItemStackHandler instance, Direction side) {
        CompoundTag tag = instance.serializeNBT();
        return tag;
    }

    @Override
    public void readNBT(Capability<ItemStackHandler> capability, ItemStackHandler instance, Direction side, Tag nbt) {
        CompoundTag tag = (CompoundTag) nbt;
        instance.deserializeNBT(tag);
    }
}
