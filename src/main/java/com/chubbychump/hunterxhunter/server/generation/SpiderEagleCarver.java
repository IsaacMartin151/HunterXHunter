package com.chubbychump.hunterxhunter.server.generation;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.Random;
import java.util.function.Function;

public class SpiderEagleCarver extends WorldCarver<CanyonCarverConfiguration> {
    Random random = new Random();

    public SpiderEagleCarver(Codec c) {
        super(c);
    }

    @Override
    public boolean carve(CarvingContext p_224913_, CanyonCarverConfiguration p_224914_, ChunkAccess p_224915_, Function<BlockPos, Holder<Biome>> p_224916_, RandomSource p_224917_, Aquifer p_224918_, ChunkPos p_224919_, CarvingMask p_224920_) {
        return random.nextBoolean();
    }

    @Override
    public boolean isStartChunk(CanyonCarverConfiguration p_224908_, RandomSource p_224909_) {
        return false;
    }
}
