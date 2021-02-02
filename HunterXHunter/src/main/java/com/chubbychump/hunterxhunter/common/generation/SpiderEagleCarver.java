package com.chubbychump.hunterxhunter.common.generation;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.SPIDER_EAGLE_EGG_BLOCK;
import static com.chubbychump.hunterxhunter.util.RegistryHandler.SUPER_COBWEB;

public class SpiderEagleCarver extends WorldCarver<ProbabilityConfig> {
    private final float[] field_202536_i = new float[1024];

    public SpiderEagleCarver(Codec p_i231921_1_, int p_i231921_2_) {
        super(p_i231921_1_, p_i231921_2_);
    }

    @Override
    public boolean carveRegion(IChunk chunk, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel, int chunkXOffset, int chunkZOffset, int chunkX, int chunkZ, BitSet carvingMask, ProbabilityConfig config) {
        int i = (this.func_222704_c() * 2 - 1) * 16;
        double d0 = (double)(chunkXOffset * 16 + rand.nextInt(16) + 30);
        double d1 = (double)(rand.nextInt(10) + 50);
        double d2 = (double)(chunkZOffset * 16 + rand.nextInt(16) + 30);
        float rand1 = rand.nextFloat() * ((float)Math.PI * 2F);
        float rand2 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
        float f2 = (rand.nextFloat() * 2.0F + rand.nextFloat()) * 2.0F;
        int j = i - rand.nextInt(i / 4);
        this.carve(chunk, biomePos, rand.nextLong(), seaLevel, chunkX, chunkZ, d0, d1, d2, f2, rand1, rand2, 0, j, 3.0D, carvingMask);
        return true;
    }

    private void carve(IChunk chunk, Function<BlockPos, Biome> biomePos, long rand, int seaLevel, int chunkX, int chunkZ, double rX, double rY, double rZ, float p_227204_14_, float p_227204_15_, float p_227204_16_, int p_227204_17_, int p_227204_18_, double p_227204_19_, BitSet p_227204_21_) {
        Random random = new Random(rand);
        float f = 1.0F;
        for(int i = 0; i < 256; ++i) {
            if (i == 0 || random.nextInt(3) == 0) {
                f = 1.0F + random.nextFloat() * random.nextFloat();
            }
            this.field_202536_i[i] = f * f;
        }
        float f4 = 0.0F;
        float f1 = 0.0F;

        for(int j6 = p_227204_17_; j6 < p_227204_18_; ++j6) {
            double d0 = 1.5D + (double)(MathHelper.sin((float)j6 * (float)Math.PI / (float)p_227204_18_) * p_227204_14_);
            double d1 = d0 * p_227204_19_;
            d0 = d0 * ((double)random.nextFloat() * 0.25D + 0.75D);
            d1 = d1 * ((double)random.nextFloat() * 0.25D + 0.75D);
            float f2 = MathHelper.cos(p_227204_16_);
            float f3 = MathHelper.sin(p_227204_16_);
            rX += (double)(MathHelper.cos(p_227204_15_) * f2);
            rY += (double)f3;
            rZ += (double)(MathHelper.sin(p_227204_15_) * f2);
            p_227204_16_ = p_227204_16_ * 0.7F;
            p_227204_16_ = p_227204_16_ + f1 * 0.05F;
            p_227204_15_ += f4 * 0.05F;
            f1 = f1 * 0.8F;
            f4 = f4 * 0.5F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (random.nextInt(4) != 0) {
                if (!this.func_222702_a(chunkX, chunkZ, rX, rZ, j6, p_227204_18_, p_227204_14_)) {
                    return;
                }


                this.func_227208_a_(chunk, biomePos, rand, seaLevel, chunkX, chunkZ, rX, rY, rZ, d0, d1, p_227204_21_);

                //added stuff here
                int i = Math.max(MathHelper.floor(rX - d0) - chunkX * 16 - 1, 0);
                int j = Math.min(MathHelper.floor(rX + d0) - chunkX * 16 + 1, 16);
                int k = Math.max(MathHelper.floor(rY - d1) - 1, 1);
                int l = Math.min(MathHelper.floor(rY + d1) + 1, this.maxHeight - 8);
                int i1 = Math.max(MathHelper.floor(rZ - d0) - chunkZ * 16 - 1, 0);
                int j1 = Math.min(MathHelper.floor(rZ + d0) - chunkZ * 16 + 1, 16);
                int horizontal = random.nextInt(64) + 1;
                int yrand = random.nextInt(20);
                for(int k1 = i; k1 < j; ++k1) {
                    int l1 = k1 + chunkX * 16;
                    double d2 = ((double)l1 + 0.5D - rX) / d0;
                    for(int i2 = i1; i2 < j1; ++i2) {
                        int j2 = i2 + chunkZ * 16;
                        double d3 = ((double)j2 + 0.5D - rZ) / d0;
                        if (!(d2 * d2 + d3 * d3 >= 1.0D)) {
                            for(int k2 = l; k2 > k; --k2) {
                                double d4 = ((double)k2 - 0.5D - rY) / d1;
                                if (!this.func_222708_a(d2, d4, d3, k2)) {
                                    //flag |= this.carveBlock(chunk, biomePos, carvingMask, random, blockpos$mutable, blockpos$mutable1, blockpos$mutable2, seaLevel, chunkX, chunkZ, l1, j2, k1, k2, i2, mutableboolean);
                                    BlockPos change = new BlockPos(l1, k2 + yrand, j2);
                                    //HunterXHunter.LOGGER.info("Setting blockpos "+change.getX()+", "+change.getY()+", "+change.getZ());
                                    if (k2 < 40 && k2 > 20 && (l1 % horizontal == 20 || l1 % horizontal == 61 || j2 % horizontal == 16 || j2 % horizontal == 57)) {
                                        chunk.setBlockState(change, Blocks.COBWEB.getDefaultState(), false);
                                        BlockPos changePlus = new BlockPos(l1, k2 +yrand+1, j2);
                                        if (chunk.getBlockState(changePlus) != Blocks.COBWEB.getDefaultState()) {
                                            chunk.setBlockState(changePlus, SPIDER_EAGLE_EGG_BLOCK.get().getDefaultState(), false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldCarve(Random rand, int chunkX, int chunkZ, ProbabilityConfig config) {
        return rand.nextFloat() <= config.probability;
    }

    @Override
    protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_) {
        return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * (double)this.field_202536_i[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
    }
}
