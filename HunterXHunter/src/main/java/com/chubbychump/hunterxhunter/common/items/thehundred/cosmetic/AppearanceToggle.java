package com.chubbychump.hunterxhunter.common.items.thehundred.cosmetic;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.block.WebBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AppearanceToggle extends Item {

    private boolean isOn = false;

    public AppearanceToggle() {
        super(new Item.Properties().group(HunterXHunter.TAB).rarity(Rarity.EPIC).maxStackSize(1));
        isOn = false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        HunterXHunter.LOGGER.info("Toggled on");
        isOn = !isOn;
        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }

    public boolean isOn() {
        return isOn;
    }

    public void renderNewAppearance() {
        HunterXHunter.LOGGER.info("Rendering new appearance");
    }
}
