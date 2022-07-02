package com.chubbychump.hunterxhunter.server.generation.structures.worldtree;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import java.util.Set;

public abstract class WorldTreeBaseComponent extends StructurePiece {
    public WorldTreeDecorator deco = null;
    public int spawnListIndex = 0;
    protected Feature feature = null;
    private static final Set<Block> BLOCKS_NEEDING_POSTPROCESSING = ImmutableSet.<Block>builder()
            .add(Blocks.NETHER_BRICK_FENCE)
            .add(Blocks.TORCH)
            .add(Blocks.WALL_TORCH)
            .add(Blocks.OAK_FENCE)
            .add(Blocks.SPRUCE_FENCE)
            .add(Blocks.DARK_OAK_FENCE)
            .add(Blocks.ACACIA_FENCE)
            .add(Blocks.BIRCH_FENCE)
            .add(Blocks.JUNGLE_FENCE)
            .add(Blocks.LADDER)
            .add(Blocks.IRON_BARS)
            .add(Blocks.GLASS_PANE)
            .add(Blocks.OAK_STAIRS)
            .add(Blocks.SPRUCE_STAIRS)
            .add(Blocks.BIRCH_STAIRS)
            .add(Blocks.COBBLESTONE_WALL)
            .add(Blocks.RED_MUSHROOM_BLOCK)
            .add(Blocks.BROWN_MUSHROOM_BLOCK)
            .add(Blocks.REDSTONE_WIRE)
            .add(Blocks.CHEST)
            .add(Blocks.TRAPPED_CHEST)
            .add(Blocks.STONE_BRICK_STAIRS)
            .build();


    public WorldTreeBaseComponent(IStructurePieceType piece, CompoundNBT nbt) {
        super(piece, nbt);
        this.spawnListIndex = nbt.getInt("si");
        this.deco = new WorldTreeDecorator();
    }

    public WorldTreeBaseComponent(IStructurePieceType type, int i) {
        super(type, i);
    }

    public WorldTreeBaseComponent(IStructurePieceType type, Feature feature, int i) {
        this(type, i);
        this.feature = feature;
    }

    @Override
    protected void readAdditional(CompoundNBT tagCompound) {
        tagCompound.putInt("si", this.spawnListIndex);
    }

    public static MutableBoundingBox getComponentToAddBoundingBox2(int x, int y, int z, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Direction dir) {
        switch (dir) {
            default:
                return new MutableBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY, z + maxZ + minZ);

            case SOUTH: // '\0'
                return new MutableBoundingBox(x + minX, y + minY, z + minZ, x + maxX + minX, y + maxY + minY, z + maxZ + minZ);

            case WEST: // '\001'
                return new MutableBoundingBox(x - maxZ - minZ, y + minY, z + minX, x - minZ, y + maxY + minY, z + maxX + minX);

            case NORTH: // '\002'
                return new MutableBoundingBox(x - maxX - minX, y + minY, z - maxZ - minZ, x - minX, y + maxY + minY, z - minZ);

            case EAST: // '\003'
                return new MutableBoundingBox(x + minZ, y + minY, z - maxX, x + maxZ + minZ, y + maxY + minY, z - minX);
        }
    }
}
