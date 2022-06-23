package com.chubbychump.hunterxhunter.common.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import static net.minecraft.item.Rarity.COMMON;

public class TastyFood extends Item {
    public TastyFood() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(COMMON).food(new Food.Builder()
                .saturation(0.8F).hunger(8).effect(new EffectInstance(Effects.REGENERATION, 100, 1), .2F).build()));
    }
}
