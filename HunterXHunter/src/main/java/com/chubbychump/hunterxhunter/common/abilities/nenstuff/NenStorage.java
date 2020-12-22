package com.chubbychump.hunterxhunter.common.abilities.nenstuff;

import com.chubbychump.hunterxhunter.common.abilities.nenstuff.NenUser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;

/**
 * This class is responsible for saving and reading nen data from or to server
 */
public class NenStorage implements Capability.IStorage<NenUser> {
    @Override
    public INBT writeNBT(Capability<NenUser> capability, NenUser instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("currentnen", instance.getCurrentNen());
        tag.putInt("nenpower", instance.getNenPower());
        tag.putBoolean("nenactivated", instance.isNenActivated());
        tag.putBoolean("gyo", instance.getGyo());
        tag.putBoolean("en", instance.getEn());
        tag.putBoolean("ren", instance.getRen());
        tag.putBoolean("zetsu", instance.getZetsu());

        return tag;
    }

    @Override
    public void readNBT(Capability<NenUser> capability, NenUser instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setCurrentNen(tag.getInt("currentnen"));
        instance.setNenPower(tag.getInt("nenpower"));
        instance.nenActivated = tag.getBoolean("nenactivated");
        instance.gyo = tag.getBoolean("gyo");
        instance.en = tag.getBoolean("en");
        instance.ren = tag.getBoolean("ren");
        instance.zetsu = tag.getBoolean("zetsu");
    }
}
