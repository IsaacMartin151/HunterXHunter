package com.chubbychump.hunterxhunter.common.abilities.heartstuff;

import net.minecraft.entity.player.PlayerEntity;

public interface IMoreHealth {
    byte getVersion();
    float getModifier();
    float getTrueModifier();
    short getRampPosition();
    byte getHeartContainers();
    void setVersion(byte version);
    void setModifier(float modifier);
    void setRampPosition(short position);
    void addRampPosition();
    void setHeartContainers(byte amount);
    void addHeartContainer();
    void copy(IMoreHealth other);
    void synchronise(PlayerEntity player);

}
