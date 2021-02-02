package com.chubbychump.hunterxhunter.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class AuraStone extends OreBlock {
    public AuraStone() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(3.0f, 3.0f)
                .sound(SoundType.METAL)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE));
    }

    @Override
    protected int getExperience(Random rand) {
        return 5;
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}
