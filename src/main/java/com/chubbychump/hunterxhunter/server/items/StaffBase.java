package com.chubbychump.hunterxhunter.server.items;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class StaffBase extends Item {
    public StaffBase() {
        super(new Item.Properties().group(HunterXHunter.TAB).maxDamage(100).rarity(Rarity.UNCOMMON));
    }


}
