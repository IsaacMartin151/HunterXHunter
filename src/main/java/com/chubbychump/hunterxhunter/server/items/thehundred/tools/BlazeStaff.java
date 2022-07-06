package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;

import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BlazeStaff extends StaffBase {
    public BlazeStaff() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getMainHandItem();
        if (!worldIn.isClientSide) {
            itemstack.hurtAndBreak(1, playerIn, (player) -> {
                player.broadcastBreakEvent(handIn);
            });
            LavaFluid bro = new LavaFluid.Source();
            Vec3 pc = new Vec3(playerIn.getX(), playerIn.getY(), playerIn.getZ());
            for (int x = -2; x < 3; x++) {
                for (int z = -2; z < 3; z++) {
                    if (z < 2 && z > -2 && x < 2 && x > -2) {
                        break;
                    }
                    BlockPos pos1 = new BlockPos(pc.x + x, pc.y - 1, pc.z + z);
                    worldIn.setBlock(pos1, bro.defaultFluidState().createLegacyBlock(), 11);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Spawns a ring of lava"));
    }
}
