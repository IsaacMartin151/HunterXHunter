package com.chubbychump.hunterxhunter.server.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import static net.minecraft.world.item.Rarity.COMMON;


public class PotatoSoup extends Item {
    public PotatoSoup() {
        super(new Item.Properties().stacksTo(64).tab(HunterXHunter.TAB).rarity(COMMON).food(new FoodProperties.Builder()
                .saturationMod(1.0F).nutrition(1).build()));
    }
}
