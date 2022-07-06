package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.server.items.StaffBase;

import net.minecraft.network.chat.Component;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class PhantomStaff extends StaffBase {
    private boolean clipping = false;

    public PhantomStaff() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getMainHandItem();
        HunterXHunter.LOGGER.info("Toggled clipping");
        clipping = !clipping;
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    public boolean isOn() {
        return clipping;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Right click to toggle, allows player to phase through walls"));
    }
}
