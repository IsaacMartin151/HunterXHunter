package com.chubbychump.hunterxhunter.server.potions;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;

import javax.annotation.Nullable;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.BLOODLUST_POTION;

public class BloodLust extends Potion {

    public static Potion getPotionTypeForName(String name) {
        return BLOODLUST_POTION.get();
    }

    public BloodLust(EffectInstance... effectsIn) {
        super(null, effectsIn);
    }

    public BloodLust(@Nullable String baseNameIn, EffectInstance... effectsIn) {
        super(baseNameIn, effectsIn);
    }

    @Override
    public boolean hasInstantEffect() {
        return false;
    }
}
