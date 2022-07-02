package com.chubbychump.hunterxhunter.server.generation.structures.worldtree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class WorldTreeConfig2 implements IFeatureConfig {
    public static final Codec<WorldTreeConfig2> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("trunk_radius").forGetter(WorldTreeConfig2::getTrunkRadius),
            Codec.INT.fieldOf("branch_count").forGetter(WorldTreeConfig2::getBranchCount),
            Codec.INT.fieldOf("tree_height").forGetter(WorldTreeConfig2::getTreeHeight),
            Codec.DOUBLE.fieldOf("tall_chance").forGetter(WorldTreeConfig2::getTallChance)
    ).apply(instance, WorldTreeConfig2::new));

    private final int trunkRadius;
    private final int branchCount;
    private final int treeHeight;
    private final double tallChance;

    public WorldTreeConfig2(int trunkRadius, int branchCount, int treeHeight, double tallChance) {
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
