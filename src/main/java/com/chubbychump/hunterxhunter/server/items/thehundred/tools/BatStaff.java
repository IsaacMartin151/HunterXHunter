package com.chubbychump.hunterxhunter.server.items.thehundred.tools;

import com.chubbychump.hunterxhunter.server.items.StaffBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BatStaff extends StaffBase {
    public BatStaff() {
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, Player playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            itemstack.damageItem(1, playerIn, (player) -> {
                player.sendBreakAnimation(handIn);
            });
            BatEntity bruh = new BatEntity(EntityType.BAT, worldIn);
            bruh.setPosition(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
            playerIn.world.addEntity(bruh);

        }
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("Spawns a bat"));
    }
}

