package com.chubbychump.hunterxhunter.common.blocks;

import com.chubbychump.hunterxhunter.common.recipes.VatRecipes;
import com.chubbychump.hunterxhunter.common.tileentities.VatTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

import static com.chubbychump.hunterxhunter.util.RegistryHandler.VAT_TILE_ENTITY;

public class Vat extends Block {
    public Vat() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(1.0f, 1.0f)
                .sound(SoundType.METAL)
                .variableOpacity()
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1));
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new VatTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof ItemEntity) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof VatTileEntity) {
                ((VatTileEntity)tileentity).onEntityCollision(entityIn);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.5D;
        double d2 = (double)pos.getZ() + 0.5D;
        if (rand.nextDouble() < 0.1D) {
            worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
        double d4 = rand.nextDouble() * 0.6D - 0.3D;
        double d5 = d4;
        double d6 = rand.nextDouble() * 6.0D / 16.0D;
        double d7 = d4;
        worldIn.addParticle(ParticleTypes.BUBBLE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 1.0D, 0.0D);
    }
}
