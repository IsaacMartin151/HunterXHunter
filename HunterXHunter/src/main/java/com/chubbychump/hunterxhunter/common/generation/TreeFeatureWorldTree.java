package com.chubbychump.hunterxhunter.common.generation;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;

public class TreeFeatureWorldTree extends TreeFeature {
    public TreeFeatureWorldTree(Codec<BaseTreeFeatureConfig> p_i231999_1_) {
        super(p_i231999_1_);
    }

    private boolean place(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundingBoxIn, BaseTreeFeatureConfig configIn) {
        WorldTreeTrunkPlacer yeet = new WorldTreeTrunkPlacer(31, 23, 23);
        int i = yeet.func_236917_a_(rand);
        int j = configIn.foliagePlacer.func_230374_a_(rand, i, configIn);
        int k = i - j;
        int l = configIn.foliagePlacer.func_230376_a_(rand, k);
        BlockPos blockpos;
        if (!configIn.forcePlacement) {
            int i1 = generationReader.getHeight(Heightmap.Type.OCEAN_FLOOR, positionIn).getY();
            int j1 = generationReader.getHeight(Heightmap.Type.WORLD_SURFACE, positionIn).getY();
            if (j1 - i1 > configIn.maxWaterDepth) {
                return false;
            }

            int k1;
            if (configIn.field_236682_l_ == Heightmap.Type.OCEAN_FLOOR) {
                k1 = i1;
            } else if (configIn.field_236682_l_ == Heightmap.Type.WORLD_SURFACE) {
                k1 = j1;
            } else {
                k1 = generationReader.getHeight(configIn.field_236682_l_, positionIn).getY();
            }

            blockpos = new BlockPos(positionIn.getX(), k1, positionIn.getZ());
        } else {
            blockpos = positionIn;
        }

        if (blockpos.getY() >= 1 && blockpos.getY() + i + 1 <= 256) {
            if (!isDirtOrFarmlandAt(generationReader, blockpos.down())) {
                return false;
            } else {
                OptionalInt optionalint = configIn.minimumSize.func_236710_c_();
                int l1 = this.func_241521_a_(generationReader, i, blockpos, configIn);
                if (l1 >= i || optionalint.isPresent() && l1 >= optionalint.getAsInt()) {
                    List<FoliagePlacer.Foliage> list = yeet.func_230382_a_(generationReader, rand, l1, blockpos, p_225557_4_, boundingBoxIn, configIn);
                    list.forEach((p_236407_8_) -> {
                        configIn.foliagePlacer.func_236752_a_(generationReader, rand, configIn, l1, p_236407_8_, j, l, p_225557_5_, boundingBoxIn);
                    });
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private int func_241521_a_(IWorldGenerationBaseReader p_241521_1_, int p_241521_2_, BlockPos p_241521_3_, BaseTreeFeatureConfig p_241521_4_) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int i = 0; i <= p_241521_2_ + 1; ++i) {
            int j = p_241521_4_.minimumSize.func_230369_a_(p_241521_2_, i);

            for(int k = -j; k <= j; ++k) {
                for(int l = -j; l <= j; ++l) {
                    blockpos$mutable.setAndOffset(p_241521_3_, k, i, l);
                    if (!func_236410_c_(p_241521_1_, blockpos$mutable) || !p_241521_4_.ignoreVines && func_236414_e_(p_241521_1_, blockpos$mutable)) {
                        return i - 2;
                    }
                }
            }
        }

        return p_241521_2_;
    }

    private static boolean func_236414_e_(IWorldGenerationBaseReader p_236414_0_, BlockPos p_236414_1_) {
        return p_236414_0_.hasBlockState(p_236414_1_, (p_236415_0_) -> {
            return p_236415_0_.isIn(Blocks.VINE);
        });
    }

    public static boolean isAirOrLeavesAt(IWorldGenerationBaseReader p_236412_0_, BlockPos p_236412_1_) {
        return p_236412_0_.hasBlockState(p_236412_1_, (p_236411_0_) -> {
            return p_236411_0_.isAir() || p_236411_0_.isIn(BlockTags.LEAVES);
        });
    }

    private static boolean isDirtOrFarmlandAt(IWorldGenerationBaseReader p_236418_0_, BlockPos p_236418_1_) {
        return p_236418_0_.hasBlockState(p_236418_1_, (p_236409_0_) -> {
            Block block = p_236409_0_.getBlock();
            return isDirt(block) || block == Blocks.FARMLAND;
        });
    }
}
