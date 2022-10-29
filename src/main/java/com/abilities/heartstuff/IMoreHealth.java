package com.abilities.heartstuff;

import net.minecraft.entity.player.Player;

public interface IMoreHealth {
    byte getVersion();
    float getModifier();
    float getEnhancerModifier();
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
    void synchronise(Player player);

}
