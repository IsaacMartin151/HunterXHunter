package com.chubbychump.hunterxhunter.common.items.thehundred.tools;

import com.chubbychump.hunterxhunter.common.items.StaffBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GuardianStaff extends StaffBase {
    public GuardianStaff() {
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            itemstack.damageItem(1, playerIn, (player) -> {
                player.sendBreakAnimation(handIn);
            });
            playerIn.addPotionEffect(new EffectInstance(Effects.HASTE, 600));

        }
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}
