package com.chubbychump.hunterxhunter.server.potions;


import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BloodLustEffect extends MobEffect {
    public BloodLustEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("BloodLust");
    }
}
