package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GuardianStaff extends StaffBase {
    public GuardianStaff() {
        super();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getMainHandItem();
        if (!worldIn.isClientSide) {
            itemstack.hurtAndBreak(1, playerIn, (player) -> {
                player.broadcastBreakEvent(handIn);
            });
            playerIn.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 600));

        }
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Gives Haste"));
    }
}
