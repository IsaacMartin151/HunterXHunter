package com.chubbychump.hunterxhunter.common.generation.structures.floating;

import com.chubbychump.hunterxhunter.common.generation.structures.floating.GravityMineralsConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class GravityMineralsConfig implements IFeatureConfig {
        public static final Codec<com.chubbychump.hunterxhunter.common.generation.structures.floating.GravityMineralsConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("trunk_radius").forGetter(com.chubbychump.hunterxhunter.common.generation.structures.floating.GravityMineralsConfig::getTrunkRadius),
                Codec.INT.fieldOf("branch_count").forGetter(com.chubbychump.hunterxhunter.common.generation.structures.floating.GravityMineralsConfig::getBranchCount),
                Codec.INT.fieldOf("tree_height").forGetter(com.chubbychump.hunterxhunter.common.generation.structures.floating.GravityMineralsConfig::getTreeHeight),
                Codec.DOUBLE.fieldOf("tall_chance").forGetter(com.chubbychump.hunterxhunter.common.generation.structures.floating.GravityMineralsConfig::getTallChance)
        ).apply(instance, com.chubbychump.hunterxhunter.common.generation.structures.floating.GravityMineralsConfig::new));

        private final int trunkRadius;
        private final int branchCount;
        private final int treeHeight;
        private final double tallChance;

        public GravityMineralsConfig(int trunkRadius, int branchCount, int treeHeight, double tallChance) {
            this.trunkRadius = trunkRadius;
            this.branchCount = branchCount;
            this.treeHeight = treeHeight;
            this.tallChance = tallChance;
        }

        public int getTrunkRadius() {
            return trunkRadius;
        }

        public int getBranchCount() {
            return branchCount;
        }

        public int getTreeHeight() {
            return treeHeight;
        }

        public double getTallChance() {
            return tallChance;
        }
}
