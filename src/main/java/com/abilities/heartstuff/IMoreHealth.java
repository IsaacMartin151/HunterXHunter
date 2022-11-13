package com.abilities.heartstuff;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMoreHealth extends INBTSerializable<CompoundTag> {
    byte getVersion();
    float getModifier();
    short getRampPosition();
    byte getHeartContainers();
    void setVersion(byte version);
    void setModifier(float modifier);
    void setRampPosition(short rampPosition);
    void setHeartContainers(byte heartContainers);
    void addRampPosition();
    float getEnhancerModifier();
    float getTrueModifier();
    void addHeartContainer();
    void copy(IMoreHealth other);
    void synchronise(Player player);

}
