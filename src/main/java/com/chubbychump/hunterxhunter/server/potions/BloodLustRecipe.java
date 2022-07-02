package com.chubbychump.hunterxhunter.server.potions;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.Collections;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.*;

public class BloodLustRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input2) {
        return input2.getItem() == Items.POTION && PotionUtils.getEffectsFromStack(input2) == Potions.MUNDANE;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient2) {
        return ingredient2.getItem() == CARAPACE.get();
    }

    @Override
    public ItemStack getOutput(ItemStack input2, ItemStack ingredient2) {
        if (PotionUtils.getPotionFromItem(input2) == Potions.MUNDANE) {
            return PotionUtils.appendEffects(Items.POTION.getDefaultInstance(), Collections.singletonList(new EffectInstance(BLOODLUST_EFFECT.get(), 600)));
        }
        return input2;
    }
}
