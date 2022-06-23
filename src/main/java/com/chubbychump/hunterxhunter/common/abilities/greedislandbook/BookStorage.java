package com.chubbychump.hunterxhunter.common.abilities.greedislandbook;

import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

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
