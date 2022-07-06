package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;
import com.mojang.math.Vector3d;

import net.minecraft.network.chat.Component;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GhastStaff extends StaffBase {
    public GhastStaff() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getMainHandItem();
        if (!worldIn.isClientSide) {
            itemstack.hurt(1, RandomSource.create(), (ServerPlayer) playerIn);
            Vec3 yo = playerIn.getLookAngle();
            Fireball fireballentity = new LargeFireball(worldIn, playerIn, yo.x*20, yo.y*20, yo.z*20, 3);
            fireballentity.setPos(playerIn.getX() + yo.x, playerIn.getEyeY() + yo.y, playerIn.getZ() + yo.z);
            worldIn.addFreshEntity(fireballentity);
            worldIn.broadcastEntityEvent((Player)null, (byte) 1016);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Launches an explosive fireball"));
    }
}
