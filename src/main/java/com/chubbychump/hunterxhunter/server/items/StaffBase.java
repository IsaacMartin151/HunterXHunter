package com.chubbychump.hunterxhunter.server.items;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;


public class StaffBase extends Item {
    public StaffBase() {
        super(new Item.Properties().tab(HunterXHunter.TAB).durability(100).rarity(Rarity.UNCOMMON));
    }


}
