package com.chubbychump.hunterxhunter.server.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;

import net.minecraft.world.effect.MobMobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static net.minecraft.world.item.Rarity.UNCOMMON;


public class SpiderEagleEgg extends Item{
    public SpiderEagleEgg() {
        super(new Item.Properties().stacksTo(64).tab(HunterXHunter.TAB).rarity(UNCOMMON).food(new FoodProperties.Builder()
                .saturationMod(1.6F).nutrition(16).effect(new MobMobEffectInstance(MobEffects.SLOW_FALLING, 500, 1), 1F).alwaysEat().build()));
    }
}
