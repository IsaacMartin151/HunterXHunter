package com.chubbychump.hunterxhunter.common.items.thehundred.tools;

import com.chubbychump.hunterxhunter.common.items.StaffBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class GhastStaff extends StaffBase {
    public GhastStaff() {
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            itemstack.damageItem(1, playerIn, (player) -> {
                player.sendBreakAnimation(handIn);
            });
            Vector3d yo = playerIn.getLookVec();
            FireballEntity fireballentity = new FireballEntity(worldIn, playerIn, yo.x*20, yo.y*20, yo.z*20);
            fireballentity.explosionPower = 2;
            fireballentity.setPosition(playerIn.getPosX() + yo.x, playerIn.getPosYHeight(0.5D) + yo.y, playerIn.getPosZ() + yo.z);
            worldIn.addEntity(fireballentity);
            worldIn.playEvent((PlayerEntity)null, 1016, playerIn.getPosition(), 0);
        }
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}
