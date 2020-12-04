package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

/**
 * This class is responsible for saving and reading mana data from or to server
 */
public class NenStorage implements Capability.IStorage<NenUser>
{
    @Override
    public INBT writeNBT(Capability<NenUser> capability, NenUser instance, Direction side)
    {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("currentnen", instance.getCurrentNen());
        tag.putInt("nenpower", instance.getNenPower());
        return tag;
    }

    @Override
    public void readNBT(Capability<NenUser> capability, NenUser instance, Direction side, INBT nbt)
    {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setCurrentNen(tag.getByte("currentnen"));
        instance.setCurrentNen(tag.getByte("nenpower"));
    }
}