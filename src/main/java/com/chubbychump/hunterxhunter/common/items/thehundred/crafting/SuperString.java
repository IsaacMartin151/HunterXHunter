package com.chubbychump.hunterxhunter.common.items.thehundred.crafting;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Item;

import static net.minecraft.item.Rarity.UNCOMMON;

public class SuperString extends Item {
    public SuperString() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(UNCOMMON));
    }
}