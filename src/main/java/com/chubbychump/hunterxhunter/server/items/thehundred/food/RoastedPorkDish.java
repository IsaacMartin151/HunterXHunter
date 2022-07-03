package com.chubbychump.hunterxhunter.server.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static net.minecraft.world.item.Rarity.UNCOMMON;


public class RoastedPorkDish extends Item { //1/100 chance of getting when cooking porkchop
    public RoastedPorkDish() {
        super(new Item.Properties().stacksTo(64).tab(HunterXHunter.TAB).rarity(UNCOMMON).food(new FoodProperties.Builder()
                .saturationMod(2.0F).nutrition(20).effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 500, 2), 1F).build()));
    }
}
