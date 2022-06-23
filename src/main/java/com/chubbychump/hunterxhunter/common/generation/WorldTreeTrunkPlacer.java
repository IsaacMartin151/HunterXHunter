package com.chubbychump.hunterxhunter.common.generation;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.structure.StrongholdPieces;
import net.minecraft.world.gen.feature.structure.StrongholdStructure;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.placement.CaveEdge;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class WorldTreeTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<WorldTreeTrunkPlacer> field_236882_a_ = RecordCodecBuilder.create((p_236883_0_) -> {
        return func_236915_a_(p_236883_0_).apply(p_236883_0_, WorldTreeTrunkPlacer::new);
    });

    public WorldTreeTrunkPlacer(int p_i232053_1_, int p_i232053_2_, int p_i232053_3_) {
        super(p_i232053_1_, p_i232053_2_, p_i232053_3_);
    }

    @Override
    protected TrunkPlacerType<?> func_230381_a_() {
        return TrunkPlacerType.FORKING_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader p_230382_1_, Random p_230382_2_, int p_230382_3_, BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_, BaseTreeFeatureConfig p_230382_7_) {
        HunterXHunter.LOGGER.info("In world generator");
        List<FoliagePlacer.Foliage> list = Lists.newArrayList();
        Random RNG = p_230382_2_;
        int radius = 12;
        int treeX = p_230382_4_.getX();
        int treeY = p_230382_4_.getY();
        int treeZ = p_230382_4_.getZ();
        int ceiling = treeY + p_230382_3_ - 1;
        int signX = 0;
        if (treeX != 0) {
            signX = treeX / Math.abs(treeX);
        }
        int xValMin = (int) ((treeX) /16.0);
        int xValMax = ((treeX + (radius*2 * signX)) /16);
        int signZ = 0;
        if (treeZ != 0) {
            signZ = treeZ / Math.abs(treeZ);
        }
        int zValMin = (int) ((treeZ) /16.0);
        int zValMax = ((treeZ + (radius*2 * signZ)) /16);

        int xChunk = (int) (treeX/16.0);
        int zChunk = (int) (treeZ/16.0);



        HunterXHunter.LOGGER.info("Xvalmin "+(xValMin));
        HunterXHunter.LOGGER.info("Xvalmax "+(xValMax));
        HunterXHunter.LOGGER.info("Zvalmin "+(zValMin));
        HunterXHunter.LOGGER.info("Zvalmax "+(zValMax));
        HunterXHunter.LOGGER.info("Chunk is "+xChunk+", "+zChunk);
        //Build column at this position
        if (RNG.nextInt(40) == 0) {
            for (int y = 0; y < p_230382_3_; ++y) {
                int j2 = treeY + y;
                BlockPos blockpos1 = new BlockPos(treeX, j2, treeZ);

                if (((blockpos1.getX() + radius * signX) / 16 >= xChunk-1 && (blockpos1.getX() + radius * signX) / 16 <= xChunk+1) && (blockpos1.getZ() + radius * signZ) / 16 >= zChunk-1 && (blockpos1.getZ() + radius * signZ) / 16 <= zChunk+1) {
                    if (((blockpos1.getX() + 2 * radius * signX) / 16 >= xChunk-1) && ((blockpos1.getX() + 2 * radius * signX) / 16 <= xChunk+1) && (blockpos1.getZ() + 2 * radius * signZ) / 16 >= zChunk-1 && (blockpos1.getZ() + 2 * radius * signZ) / 16 <= zChunk+1) {
                        HunterXHunter.LOGGER.info("In bounds");
                        for (int i = -radius - 4; i < radius + 5; i++) {
                            for (int j = -radius - 4; j < radius + 5; j++) {
                                if (Math.sqrt(((i+.5*signX) * (i+.5*signX)) + ((j+.5*signZ) * (j+.5*signZ))) <= radius) {
                                    HunterXHunter.LOGGER.info("Within the ring");
                                    BlockPos current = new BlockPos(treeX + i + radius * signX, j2, treeZ + j + radius * signZ);
                                    if (TreeFeature.isAirOrLeavesAt(p_230382_1_, blockpos1)) {

                                        placeBlock(p_230382_1_, RNG, current, p_230382_5_, p_230382_6_, p_230382_7_);
                                        //list.add(new FoliagePlacer.Foliage(new BlockPos(treeX, ceiling, treeZ), 0, true));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //Add leaves around this position
            //list.add(new FoliagePlacer.Foliage(new BlockPos(treeX, ceiling, treeZ), 0, true));

            /*
            for (int i = -1; i <= 2; ++i) {
                for (int j = -1; j <= 2; ++j) {
                    if ((i < 0 || i > 1 || j < 0 || j > 1) && p_230382_2_.nextInt(3) <= 0) {
                        int j3 = p_230382_2_.nextInt(3) + 2;

                        for (int k2 = 0; k2 < j3; ++k2) {
                            func_236911_a_(p_230382_1_, p_230382_2_, new BlockPos(treeX + i, ceiling - k2 - 1, treeZ + j), p_230382_5_, p_230382_6_, p_230382_7_);
                        }

                        //list.add(new FoliagePlacer.Foliage(new BlockPos(treeX + i, ceiling, treeZ + j), 0, false));
                    }
                }
            }

             */
        }

        return list;
    }

    protected boolean placeBlock(IWorldGenerationReader worldReader, Random rng, BlockPos blockPos, Set<BlockPos> blocks, MutableBoundingBox bbox, BaseTreeFeatureConfig treeConfig) {
        if (true) {
            //TreeFeature.isReplaceableAt()
            placeBlock2(worldReader, blockPos, treeConfig.trunkProvider.getBlockState(rng, blockPos), bbox);
            blocks.add(blockPos.toImmutable());
            return true;
        } else {
            return false;
        }
    }

    protected void placeBlock2(IWorldWriter writer, BlockPos pos, BlockState state, MutableBoundingBox bbox) {
        writer.setBlockState(pos, state, 19);
        bbox.expandTo(new MutableBoundingBox(pos, pos));
    }
}
