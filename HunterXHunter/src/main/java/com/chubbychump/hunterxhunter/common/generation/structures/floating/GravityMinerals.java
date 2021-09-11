package com.chubbychump.hunterxhunter.common.generation.structures.floating;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.generation.structures.worldtree.WorldTreeConfig2;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nonnull;
import java.util.Random;

public class GravityMinerals extends Feature<WorldTreeConfig2> {
    public GravityMinerals() {
            super(WorldTreeConfig2.CODEC);
        }

        @Override
        public boolean generate(@Nonnull ISeedReader world, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull WorldTreeConfig2 config) {
            boolean any = false;

            int x = pos.getX();
            int z = pos.getZ();
            ElderGuardianRenderer
            int y = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z);
            HunterXHunter.LOGGER.info("Placing tree at x: "+x+", y: "+y+", z: "+z);
            int width = config.getTrunkRadius();

            for (int y0 = 0; y0 < config.getTreeHeight(); y0++) {
                for (int x0 = x - width - 4; x0 < x + width + 4; x0++) {
                    for (int z0 = z - width - 4; z0 < z + width + 4; z0++) {
                        double r = Math.sqrt((x0-x)*(x0-x) + (z0-z)*(z0-z));
                        double s = Math.sin(6*y0);
                        double c = Math.cos(6*y0);
                        if (r < (double) width) {
                            //HunterXHunter.LOGGER.info("Placing block at x: "+x+", y: "+(y+y0)+", z: "+z0);
                            //HunterXHunter.LOGGER.info("4*Mathcos(6*y0) is: "+4*Math.cos(6*y0));
                            //HunterXHunter.LOGGER.info("4*Mathsin(6*y0) is: "+4*Math.sin(6*y0));
                            BlockPos newPos = new BlockPos(x0 + 4*c, y + y0, z0 + 4*s);
                            world.setBlockState(newPos, Blocks.OAK_WOOD.getDefaultState(), 3);
                            any = true;
                        }
                    }
                }
            }

            int branchOut = (int) (config.getTreeHeight() / 1.2);
            for (int j = 0; j < config.getBranchCount(); j++) {
                HunterXHunter.LOGGER.info("Branch number is " + j);
                int degree =  rand.nextInt(360);
                int yLevel = rand.nextInt(config.getTreeHeight()/2) + config.getTreeHeight()/2;

                double s = Math.sin(6*yLevel);
                double c = Math.cos(6*yLevel);

                BlockPos adjustedStart = new BlockPos(pos.getX() + 4*c, pos.getY() + yLevel, pos.getZ() + 4*s);

                BlockPos bpFinal = new BlockPos(pos.getX() + branchOut * Math.cos(degree) + 4*c, yLevel + rand.nextInt(20) - 6, pos.getZ() + branchOut * Math.sin(degree) + 4*s);

                HunterXHunter.LOGGER.info("Branch number " + (j+1) + " initial | x: "+pos.getX()+", y: "+pos.getY()+", z: "+pos.getZ());
                branchRecursion(adjustedStart, bpFinal, world, rand, y);
            }

            return any;
        }

        public void branchRecursion(BlockPos current, BlockPos bpfinal, ISeedReader world, Random rand, int y) {
            sphericalBranchSegment(world, current, y);
            int xdif = bpfinal.getX() - current.getX();
            int ydif = bpfinal.getY() - current.getY();
            int zdif = bpfinal.getZ() - current.getZ();

            int bounds = Math.abs(xdif) + Math.abs(ydif) + Math.abs(zdif);

            //HunterXHunter.LOGGER.info("In branchRecursion, Bounds is " + bounds);
            HunterXHunter.LOGGER.info("BranchRecursion, current pos is x: "+current.getX()+", y: "+(y + current.getY())+", z: "+current.getZ());
            if (bounds <= 2) {
                return;
            }

            int lottery = rand.nextInt(bounds);
            BlockPos newCurrent;

            if (lottery < Math.abs(xdif)) {
                newCurrent = new BlockPos(current.getX() + Math.abs(xdif)/xdif, current.getY(), current.getZ());
            }
            else if (lottery < Math.abs(xdif) + Math.abs(ydif)) {
                newCurrent = new BlockPos(current.getX(), current.getY() + Math.abs(ydif)/ydif, current.getZ());
            }
            else {
                newCurrent = new BlockPos(current.getX(), current.getY(), current.getZ() + Math.abs(zdif)/zdif);
            }
            branchRecursion(newCurrent, bpfinal, world, rand, y);
        }

        public void sphericalBranchSegment(ISeedReader world, BlockPos pos, int yWorld) {
            int r = 6;
            for (int x = pos.getX() - r; x < pos.getX() + r + 1; x++) {
                for (int y = pos.getY() - r; y < pos.getY() + r + 1; y++) {
                    for (int z = pos.getZ() - r; z < pos.getZ() + r + 1; z++) {
                        int xd = Math.abs(x - pos.getX());
                        int yd = Math.abs(y - pos.getY());
                        int zd = Math.abs(z - pos.getZ());
                        if (xd*xd + yd*yd + zd*zd < r*r) {
                            BlockPos log = new BlockPos(x, y + yWorld, z);
                            world.setBlockState(log, Blocks.OAK_LOG.getDefaultState().mirror(Mirror.FRONT_BACK), 3);
                        }
                    }
                }
            }
        }
}
