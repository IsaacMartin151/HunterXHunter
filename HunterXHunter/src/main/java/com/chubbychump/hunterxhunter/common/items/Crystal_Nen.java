package com.chubbychump.hunterxhunter.common.items;

import com.chubbychump.hunterxhunter.Config;
import com.chubbychump.hunterxhunter.HunterXHunter;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.IMoreHealth;
import com.chubbychump.hunterxhunter.common.abilities.heartstuff.MoreHealth;
import net.minecraft.item.Item;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class Crystal_Nen extends Item {

    public Crystal_Nen() {
        super(new Properties().maxStackSize(1).group(HunterXHunter.TAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        // Setup return results
        ItemStack stack = player.getHeldItem(hand);
        ActionResult<ItemStack> result = new ActionResult<>(ActionResultType.PASS, stack);

        // Ensure server-side only & the player's not in creative or spectator
        if (world.isRemote || player.abilities.disableDamage) {
            return result;
        }

        // If disabled don't do anything
        if (!Config.enableItems.get()) {
            return result;
        }

        // Get capability
        IMoreHealth cap = MoreHealth.getFromPlayer(player);

        // If the player's at max health, or they've reached the heart container limit, only fill health bar
        int max = Config.maxHealth.get();
        if (cap.getHeartContainers() + 1 > 127 || (max > 0 && player.getMaxHealth() >= max)) {
            player.setHealth(player.getMaxHealth());
        } else {
            cap.addHeartContainer();
            MoreHealth.updateClient((ServerPlayerEntity) player, cap);
            HunterXHunter.applyHealthModifier(player, cap.getTrueModifier());
            player.setHealth(player.getMaxHealth());
            player.sendMessage(new TranslationTextComponent("Just leveled up!"), Util.field_240973_b_);
        }

        // Remove item and mark as success
        stack.setCount(stack.getCount() - 1);
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}