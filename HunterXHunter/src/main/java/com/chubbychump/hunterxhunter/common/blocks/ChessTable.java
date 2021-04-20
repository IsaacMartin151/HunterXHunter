package com.chubbychump.hunterxhunter.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ChessTable extends Block {
    public ChessTable() {
        super(Block.Properties.create(Material.GOURD)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.METAL)
                .harvestLevel(0)
                .harvestTool(ToolType.AXE));
    }
}

