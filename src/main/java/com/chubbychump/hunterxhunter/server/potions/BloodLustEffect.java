package com.chubbychump.hunterxhunter.server.potions;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
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
