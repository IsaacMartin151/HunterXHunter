package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Launches an explosive fireball"));
    }
}
