package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FoxbearStaff extends StaffBase {
    public FoxbearStaff() {
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            RayTraceResult raytraceresult = new EntityRayTraceResult(playerIn);
            BlockPos target = new BlockPos(raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
            if (worldIn.getBlockState(target).getBlock() == Blocks.COAL_ORE) {
                worldIn.addEntity(new ItemEntity(worldIn, target.getX(), target.getY(), target.getZ(), Items.GOLD_NUGGET.getDefaultInstance()));
                worldIn.setBlockState(target, Blocks.AIR.getDefaultState(), 3);
                itemstack.damageItem(1, playerIn, (player) -> {
                    player.sendBreakAnimation(handIn);
                });
            }
        }
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Turns coal ore into gold"));
    }
}