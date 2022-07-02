package com.chubbychump.hunterxhunter.server.items;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().group(HunterXHunter.TAB));
    }
}
