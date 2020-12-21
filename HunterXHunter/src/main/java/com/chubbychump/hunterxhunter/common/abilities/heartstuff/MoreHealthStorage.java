package com.chubbychump.hunterxhunter.common.abilities.heartstuff;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MoreHealthStorage implements IStorage<IMoreHealth> {
    @Override
    public INBT writeNBT(Capability<IMoreHealth> capability, IMoreHealth instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putByte("version", instance.getVersion());
        tag.putFloat("modifier", instance.getModifier());
        tag.putShort("position", instance.getRampPosition());
        tag.putByte("containers", instance.getHeartContainers());
        return tag;
    }

    @Override
    public void readNBT(Capability<IMoreHealth> capability, IMoreHealth instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setVersion(tag.getByte("version"));
        instance.setModifier(tag.getFloat("modifier"));
        instance.setRampPosition(tag.getShort("position"));
        instance.setHeartContainers(tag.getByte("containers"));
    }
}

