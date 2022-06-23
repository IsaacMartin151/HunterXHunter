package com.chubbychump.hunterxhunter.common.items.thehundred.food;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

import static net.minecraft.item.Rarity.COMMON;

public class PotatoSoup extends Item {
    public PotatoSoup() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(COMMON).food(new Food.Builder()
                .saturation(1.0F).hunger(1).build()));
    }
}
