package com.chubbychump.hunterxhunter.server.potions;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import javax.annotation.Nullable;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.BLOODLUST_POTION;

public class BloodLust extends Potion {

    public static Potion getPotionTypeForName(String name) {
        return BLOODLUST_POTION.get();
    }

    public BloodLust(MobEffectInstance... effectsIn) {
        super(null, effectsIn);
    }

    public BloodLust(@Nullable String baseNameIn, MobEffectInstance... effectsIn) {
        super(baseNameIn, effectsIn);
    }

    @Override
    public boolean hasInstantEffects() {
        return false;
    }
}
