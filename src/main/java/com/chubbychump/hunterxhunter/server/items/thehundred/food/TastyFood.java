package com.chubbychump.hunterxhunter.server.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.client.renderer.MobEffectInstance;
import net.minecraft.world.effect.MobMobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static net.minecraft.world.item.Rarity.COMMON;


public class TastyFood extends Item {
    public TastyFood() {
        super(new Item.Properties().stacksTo(64).tab(HunterXHunter.TAB).rarity(COMMON).food(new FoodProperties.Builder()
                .saturationMod(0.8F).nutrition(8).effect(() -> new MobMobEffectInstance(MobEffects.REGENERATION, 100, 1), .2F).build()));
    }
}
