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
public class NenStorage implements Capability.IStorage<INenUser> {
    @Override
    public INBT writeNBT(Capability<INenUser> capability, INenUser instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("type", instance.getNenType());
        tag.putBoolean("conjurer", instance.getConjurerActivated());
        tag.putBoolean("book", instance.openedBook());
        tag.putFloat("currentnen", instance.getCurrentNen());
        tag.putInt("nenpower", instance.getNenPower());
        tag.putInt("burnout", instance.getBurnout());
        tag.putBoolean("nenactivated", instance.getNenActivated());
        tag.putBoolean("gyo", instance.getGyo());
        tag.putBoolean("en", instance.getEn());
        tag.putBoolean("ren", instance.getRen());
        tag.putBoolean("zetsu", instance.getZetsu());
        tag.putInt("passivepower", instance.getPassivePower());
        tag.putBoolean("blocknext", instance.blockDamage());
        tag.putInt("eID", instance.getEntityID());

        return tag;
    }

    @Override
    public void readNBT(Capability<INenUser> capability, INenUser instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setType(tag.getInt("type"));
        instance.setConjurerActivated(tag.getBoolean("conjurer"));
        instance.setOpenedBook(tag.getBoolean("book"));
        instance.setCurrentNen(tag.getFloat("currentnen"));
        instance.setNenPower(tag.getInt("nenpower"));
        instance.setBurnout(tag.getInt("burnout"));
        instance.setNenActivated(tag.getBoolean("nenactivated"));
        instance.setGyo(tag.getBoolean("gyo"));
        instance.setEn(tag.getBoolean("en"));
        instance.setRen(tag.getBoolean("ren"));
        instance.setZetsu(tag.getBoolean("zetsu"));
        instance.setPassivePower(tag.getInt("passivepower"));
        instance.setBlockDamage(tag.getBoolean("blocknext"));
        if (tag.getInt("eID") != -2) {
            instance.setEntityID(tag.getInt("eID"));
        }
    }
}
