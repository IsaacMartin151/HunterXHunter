package com.chubbychump.hunterxhunter.common.blocks;

import net.minecraft.block.AirBlock;
import net.minecraft.block.material.Material;

public class NenLight extends AirBlock {
    public NenLight() {
        super(AirBlock.Properties.create(Material.AIR)
                .func_235838_a_((lightLevel) -> 15));
    }
}