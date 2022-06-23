package com.chubbychump.hunterxhunter.common.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import static net.minecraft.item.Rarity.UNCOMMON;

public class RoastedPorkDish extends Item { //1/100 chance of getting when cooking porkchop
    public RoastedPorkDish() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(UNCOMMON).food(new Food.Builder()
                .saturation(2.0F).hunger(20).effect(new EffectInstance(Effects.ABSORPTION, 500, 2), 1F).build()));
    }
}
