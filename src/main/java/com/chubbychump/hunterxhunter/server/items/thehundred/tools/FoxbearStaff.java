package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FoxbearStaff extends StaffBase {
    public FoxbearStaff() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getMainHandItem();
        if (!worldIn.isClientSide) {
            EntityHitResult raytraceresult = new EntityHitResult(playerIn);
            BlockPos target = new BlockPos(raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
            if (worldIn.getBlockState(target).getBlock() == Blocks.COAL_ORE) {
                worldIn.addFreshEntity(new ItemEntity(worldIn, target.getX(), target.getY(), target.getZ(), Items.GOLD_NUGGET.getDefaultInstance()));
                worldIn.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
                itemstack.hurtAndBreak(1, playerIn, (player) -> {
                    player.broadcastBreakEvent(handIn);
                });
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Turns coal ore into gold"));
    }
}