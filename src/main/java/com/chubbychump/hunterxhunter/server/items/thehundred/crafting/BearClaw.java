package com.chubbychump.hunterxhunter.server.items.thehundred.crafting;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Item;

import static net.minecraft.item.Rarity.UNCOMMON;

public class BearClaw extends Item {
    public BearClaw() {
        super(new Item.Properties().maxStackSize(64).group(HunterXHunter.TAB).rarity(UNCOMMON));
    }
}
