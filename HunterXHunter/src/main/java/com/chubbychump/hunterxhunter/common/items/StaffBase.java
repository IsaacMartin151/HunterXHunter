package com.chubbychump.hunterxhunter.common.items;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.ToolType;

public class StaffBase extends Item {
    public StaffBase() {
        super(new Item.Properties().group(HunterXHunter.TAB).maxDamage(100).rarity(Rarity.UNCOMMON));
    }


}
