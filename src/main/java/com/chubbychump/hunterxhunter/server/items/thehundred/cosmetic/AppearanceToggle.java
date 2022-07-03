package com.chubbychump.hunterxhunter.server.items.thehundred.cosmetic;

import com.chubbychump.hunterxhunter.HunterXHunter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;


public class AppearanceToggle extends Item {

    private boolean isOn = false;

    public AppearanceToggle() {
        super(new Item.Properties().tab(HunterXHunter.TAB).rarity(Rarity.EPIC).stacksTo(1));
        isOn = false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HunterXHunter.LOGGER.info("Toggled on");
        isOn = !isOn;
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
    }

    public boolean isOn() {
        return isOn;
    }

    public void renderNewAppearance() {
        HunterXHunter.LOGGER.info("Rendering new appearance");
    }
}
