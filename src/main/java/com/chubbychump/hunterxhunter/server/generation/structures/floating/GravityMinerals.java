package com.chubbychump.hunterxhunter.server.generation.structures.floating;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import javax.annotation.Nonnull;
import java.util.Random;

public class GravityMinerals extends Feature<GravityMineralsConfig> {
    public GravityMinerals() {
            super(GravityMineralsConfig.CODEC);
        }

    @Override
    public boolean place(FeaturePlaceContext<GravityMineralsConfig> fpc) {
        return generate(fpc.level(), fpc.origin(), fpc.config());
    }

    public boolean generate(@Nonnull WorldGenLevel world, @Nonnull BlockPos pos, @Nonnull GravityMineralsConfig config) {
        boolean any = false;

        int x = pos.getX();
        int z = pos.getZ();
        int y = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) + 30;
        HunterXHunter.LOGGER.info("Placing gravity minerals at x: "+x+", y: "+y+", z: "+z);
        int r1 = config.getShapeRadiusA();
        int r2 = config.getShapeRadiusB();
        int r3 = config.getTubeRadius();

        for (int y0 = y - r1 - r3; y0 < y + r1 + r3; y0++) {
            for (int x0 = x - r2 - r3; x0 < x + r2 + r3; x0++) {
                for (int z0 = z - r3; z0 < z + r3; z0++) {
                    double inside = r1 - Math.sqrt(z0*z0 + x0*x0);
                    double leftSide = Math.sqrt(inside * inside + y0*y0);
                    if (r3 < leftSide && r3 > -leftSide) {
                        //TODO: Fix this, just creates a vertical wall of wood
                        //HunterXHunter.LOGGER.info("Placing block at x: "+x+", y: "+(y+y0)+", z: "+z0);
                        //HunterXHunter.LOGGER.info("4*Mathcos(6*y0) is: "+4*Math.cos(6*y0));
                        //HunterXHunter.LOGGER.info("4*Mathsin(6*y0) is: "+4*Math.sin(6*y0));
                        BlockPos newPos = new BlockPos(x0, y0, z0);
                        world.setBlock(newPos, Blocks.OAK_WOOD.defaultBlockState(), 3);
                        any = true;
                    }
                }
            }
        }

        return any;
    }
}
