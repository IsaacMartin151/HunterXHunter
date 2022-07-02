package com.chubbychump.hunterxhunter.server.generation.structures.floating;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nonnull;
import java.util.Random;

public class GravityMinerals extends Feature<GravityMineralsConfig> {
    public GravityMinerals() {
            super(GravityMineralsConfig.CODEC);
        }

        @Override
        public boolean generate(@Nonnull ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull GravityMineralsConfig config) {
            boolean any = false;

            int x = pos.getX();
            int z = pos.getZ();
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z) + 30;
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
                            world.setBlockState(newPos, Blocks.OAK_WOOD.getDefaultState(), 3);
                            any = true;
                        }
                    }
                }
            }

            return any;
        }
}
