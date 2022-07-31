package com.chubbychump.hunterxhunter.server.abilities.heartstuff;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMoreHealth extends INBTSerializable<CompoundTag> {

    byte getVersion();

    float getModifier();

    float getTrueModifier();

    float getEnhancerModifier();

    short getRampPosition();

    byte getHeartContainers();

    void setVersion(byte version);

    void setModifier(float modifier);

    void setRampPosition(short position);

    void addRampPosition();

    void setHeartContainers(byte amount);

    void addHeartContainer();

    void copy(IMoreHealth other);

    void synchronise(Player player);

}
