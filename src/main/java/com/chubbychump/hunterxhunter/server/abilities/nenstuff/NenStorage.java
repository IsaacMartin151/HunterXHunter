package com.chubbychump.hunterxhunter.server.abilities.nenstuff;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

/**
 * This class is responsible for saving and reading nen data from or to server
 */
public class NenStorage implements Capability.IStorage<INenUser> {
    @Override
    public INBT writeNBT(Capability<INenUser> capability, INenUser instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("type", instance.getNenType());
        tag.putBoolean("conjurer", instance.isConjurerActivated());
        tag.putBoolean("book", instance.isOpenedBook());
        tag.putFloat("currentnen", instance.getCurrentNen());
        tag.putInt("nenpower", instance.getNenPower());
        tag.putInt("burnout", instance.getBurnout());
        tag.putBoolean("nenactivated", instance.isNenActivated());
        tag.putBoolean("gyo", instance.isGyo());
        tag.putBoolean("en", instance.isEn());
        tag.putBoolean("ren", instance.isRen());
        tag.putBoolean("zetsu", instance.isZetsu());
        tag.putInt("passivepower", instance.getPassivePower());
        tag.putBoolean("blocknext", instance.isBlockDamage());
        tag.putBoolean("riftwalk", instance.isRiftwalk());
        return tag;
    }

    @Override
    public void readNBT(Capability<INenUser> capability, INenUser instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setNenType(tag.getInt("type"));
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
        instance.setRiftwalk(tag.getBoolean("riftwalk"));
    }
}
