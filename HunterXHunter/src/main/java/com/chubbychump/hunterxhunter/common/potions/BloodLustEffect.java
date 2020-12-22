package com.chubbychump.hunterxhunter.common.potions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BloodLustEffect extends Effect {
    public BloodLustEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("BloodLust");
    }

    @Override
    public String getName() {
        return "BloodLust";
    }


}
