package com.chubbychump.hunterxhunter.common.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import static net.minecraft.item.Rarity.UNCOMMON;

public class SpiderEagleEgg extends Item{
    public SpiderEagleEgg() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(UNCOMMON).food(new Food.Builder()
                .saturation(1.6F).hunger(16).effect(new EffectInstance(Effects.SLOW_FALLING, 500, 1), 1F).build()));
    }
}
