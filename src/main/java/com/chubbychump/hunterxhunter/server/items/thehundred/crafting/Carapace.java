package com.chubbychump.hunterxhunter.server.items.thehundred.crafting;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.world.item.Item;

import static net.minecraft.world.item.Rarity.UNCOMMON;


public class Carapace extends Item {
    public Carapace() {
        super(new Item.Properties().stacksTo(64).tab(HunterXHunter.TAB).rarity(UNCOMMON));
    }
}