package com.chubbychump.hunterxhunter.common.blocks;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block) {

        super(block, new Item.Properties().group(HunterXHunter.TAB));
    }
}
