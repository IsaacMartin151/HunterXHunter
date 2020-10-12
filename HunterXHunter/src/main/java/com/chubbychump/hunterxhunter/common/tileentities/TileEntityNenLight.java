package com.chubbychump.hunterxhunter.common.tileentities;

import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.NEN_LIGHT_TILE_ENTITY;
import static java.lang.Math.abs;

public class TileEntityNenLight extends TileEntity implements ITickable {
        public Entity theEntity; // the entity holding the light-emitting item
        private boolean shouldDie;
        private static final int MAX_DEATH_TIMER = 4; // number of ticks a light source persists
        private int deathTimer;
        public boolean typeFlashlight;

        public TileEntityNenLight(TileEntityType<?> type) {
            super(type);
            shouldDie = false;
            typeFlashlight = false;
            deathTimer = MAX_DEATH_TIMER;
        }

    public TileEntityNenLight() {
         this(NEN_LIGHT_TILE_ENTITY.get());
    }

        @Override
        public void tick() {
            // check if already dying
            if (!typeFlashlight && shouldDie) {
//             // DEBUG
//             System.out.println("Should die = "+shouldDie+" with deathTimer = "+deathTimer);
                if (deathTimer > 0) {
                    deathTimer--;
                    return;
                }
                else {
                    world.setBlockState(getPos(), Blocks.AIR.getDefaultState(), 3);
                    world.removeTileEntity(getPos());;
                    return;
                }
            }

            if (theEntity == null || !theEntity.isAlive()) {
//            // DEBUG
//            System.out.println("The associated entity is null or dead");
                shouldDie = true;
                world.setBlockState(getPos(), Blocks.AIR.getDefaultState(), 3);
                world.removeTileEntity(getPos());;
                return;
            }

            if (!this.typeFlashlight) {
                 shouldDie = true;
                 world.setBlockState(getPos(), Blocks.AIR.getDefaultState(), 3);
                 world.removeTileEntity(getPos());;
            }

                /*
                 * check if entityLiving has moved away from the tile entity or no longer holding light emitting item set block to air
                 */
                double distanceSquared = abs(theEntity.lastTickPosX) + abs(theEntity.lastTickPosY) + abs(theEntity.lastTickPosZ) - abs(theEntity.getPosX()) - abs(theEntity.getPosY()) - abs(theEntity.getPosZ());
                if (distanceSquared > 5.0D) {
                    shouldDie = true;
                    return;
                }
            }
        }
        /*@Override
        public void setPos(BlockPos posIn)
        {
            pos = posIn.toImmutable();
            theEntity = world.getClosestEntity(world, pos, 2.0D);
        } */