package com.chubbychump.hunterxhunter.common.potions;

import com.google.common.collect.ImmutableList;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

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
